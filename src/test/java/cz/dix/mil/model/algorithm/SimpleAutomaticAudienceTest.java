package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.QuestionDifficulty;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests implementation of {@link SimpleAutomaticAudienceAlgorithm}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SimpleAutomaticAudienceTest {

    private static final int TEST_RUNS = 100;
    private static SimpleAutomaticAudienceAlgorithm alg = new SimpleAutomaticAudienceAlgorithm();

    @Test
    public void testFourAnswers() {
        List<Answer> answers = genAnswers(4);
        test(answers, QuestionDifficulty.EASY, 0, 15);
        test(answers, QuestionDifficulty.MID, 10, 26);
        test(answers, QuestionDifficulty.HARD, 19, 27);
    }

    @Test
    public void testTwoAnswers() {
        List<Answer> answers = genAnswers(2);
        test(answers, QuestionDifficulty.EASY, 0, 20);
        test(answers, QuestionDifficulty.MID, 20, 55);
        test(answers, QuestionDifficulty.HARD, 35, 55);
    }

    private void test(List<Answer> answers, QuestionDifficulty difficulty, int incorrectMin, int incorrectMax) {
        int lowestIncorrect = Integer.MAX_VALUE;
        int highestIncorrect = Integer.MIN_VALUE;
        int lowestCorrect = Integer.MAX_VALUE;
        int highestCorrect = Integer.MIN_VALUE;

        for (int testRunNo = 1; testRunNo <= TEST_RUNS; testRunNo++) {
            int[] res = alg.count(answers, difficulty);
            int correctMin = (100 - ((answers.size() - 1) * incorrectMax));
            int correctMax = (100 - ((answers.size() - 1) * incorrectMin));


            for (int i = 0; i < answers.size() - 1; i++) {
                Assert.assertTrue(res[i] >= incorrectMin && res[i] <= incorrectMax);
                lowestIncorrect = lowestIncorrect > res[i] ? res[i] : lowestIncorrect;
                highestIncorrect = highestIncorrect < res[i] ? res[i] : highestIncorrect;
            }
            int lastRes = res[answers.size() - 1];
            Assert.assertTrue(lastRes >= correctMin && lastRes <= correctMax);
            lowestCorrect = lowestCorrect > lastRes ? lastRes : lowestCorrect;
            highestCorrect = highestCorrect < lastRes ? lastRes : highestCorrect;

//            System.out.println(difficulty + ": " + Arrays.toString(res) + "  incorrect: <" + incorrectMin + "," + incorrectMax + ">, " +
//                    "correct: <" + correctMin + "," + correctMax + ">");
        }
        System.out.println(answers.size() + " answers " + difficulty + " min/Max incorrect: <" + lowestIncorrect + "," + highestIncorrect + ">, " +
                "correct: <" + lowestCorrect + "," + highestCorrect + ">");
    }

    private static List<Answer> genAnswers(int count) {
        List<Answer> out = new ArrayList<>();
        for (int i = 0; i < count - 1; i++) {
            char letter = (char) (65 + i);
            out.add(new Answer(String.valueOf(letter), false));
        }
        char letter = (char) (65 + count - 1);
        out.add(new Answer(String.valueOf(letter), true));
        return out;
    }
}
