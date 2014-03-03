package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.state.AudienceResult;
import cz.dix.mil.model.state.QuestionDifficulty;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests implementation of {@link SimpleAutomaticAudienceAlgorithm}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SimpleAutomaticAudienceTest {

    private static final int TEST_RUNS = 1000;
    private static SimpleAutomaticAudienceAlgorithm alg = new SimpleAutomaticAudienceAlgorithm();

    @Test
    public void testMultipleTimes() {
        Question q = new Question("", "", "",
                new Answer("A", false),
                new Answer("B", false),
                new Answer("C", false),
                new Answer("D", true));

        test(q, QuestionDifficulty.EASY, 0, 30);
        test(q, QuestionDifficulty.MID, 7, 27);
        test(q, QuestionDifficulty.HARD, 15, 30);
    }

    private void test(Question q, QuestionDifficulty difficulty, int incorrectMin, int incorrectMax) {
        for (int testRunNo = 1; testRunNo <= TEST_RUNS; testRunNo++) {
            System.out.println("Running " + difficulty + " question test #" + testRunNo);
            AudienceResult res = alg.count(q, QuestionDifficulty.EASY);
            int correctMin = (100 - (3 * incorrectMax));
            int correctMax = (100 - (3 * incorrectMin));
            System.out.println("Testing incorrect <" + incorrectMin + "," + incorrectMax + ">, " +
                    "correct: <" + correctMin + "," + correctMax + ">");
            Assert.assertTrue(res.getPercentsForA() >= incorrectMin && res.getPercentsForA() <= incorrectMax);
            Assert.assertTrue(res.getPercentsForC() >= incorrectMin && res.getPercentsForC() <= incorrectMax);
            Assert.assertTrue(res.getPercentsForD() >= incorrectMin && res.getPercentsForD() <= incorrectMax);
            Assert.assertTrue(res.getPercentsForD() >= correctMin && res.getPercentsForD() <= correctMax);
        }
    }
}
