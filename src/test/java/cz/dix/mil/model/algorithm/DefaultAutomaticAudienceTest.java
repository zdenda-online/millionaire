package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.AudienceVotingResult;
import cz.dix.mil.model.runtime.QuestionDifficulty;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests implementation of {@link DefaultAutomaticAudience}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class DefaultAutomaticAudienceTest {

    private static final int TEST_RUNS = 1000;
    private static DefaultAutomaticAudience alg = new DefaultAutomaticAudience();

    /**
     * Tests 4 possible answers (no 50-50 was used before the voting)
     */
    @Test
    public void testFourAnswers() {
        List<Answer> answers = genAnswers(4);
        test(answers, answers, QuestionDifficulty.EASY, 0, 20);
        test(answers, answers, QuestionDifficulty.MID, 10, 26);
        test(answers, answers, QuestionDifficulty.HARD, 19, 27);
    }

    /**
     * Tests 2 possible answers (50-50 was used before the voting)
     */
    @Test
    public void testTwoAnswers() {
        List<Answer> allAnswers = genAnswers(4);
        List<Answer> possibleAnswers = new ArrayList<>();
        possibleAnswers.add(allAnswers.get(1));
        possibleAnswers.add(allAnswers.get(3)); // this one is correct

        test(allAnswers, possibleAnswers, QuestionDifficulty.EASY, 0, 30);
        test(allAnswers, possibleAnswers, QuestionDifficulty.MID, 20, 55);
        test(allAnswers, possibleAnswers, QuestionDifficulty.HARD, 35, 55);
    }

    private void test(List<Answer> allAnswers, List<Answer> possibleAnswers,
                      QuestionDifficulty difficulty, int incorrectMin, int incorrectMax) {
        int lowestIncorrect = Integer.MAX_VALUE;
        int highestIncorrect = Integer.MIN_VALUE;
        int lowestCorrect = Integer.MAX_VALUE;
        int highestCorrect = Integer.MIN_VALUE;

        for (int testRunNo = 1; testRunNo <= TEST_RUNS; testRunNo++) {
            AudienceVotingResult result = alg.vote(allAnswers, possibleAnswers, difficulty);
            int correctMin = (100 - ((possibleAnswers.size() - 1) * incorrectMax));
            int correctMax = (100 - ((possibleAnswers.size() - 1) * incorrectMin));


            for (int i = 0; i < allAnswers.size() - 1; i++) {
                if (possibleAnswers.contains(allAnswers.get(i))) { // it is possible answer
                    int incorrectResult = result.getPercents(i);
                    Assert.assertTrue(incorrectResult >= incorrectMin && incorrectResult <= incorrectMax);
                    lowestIncorrect = lowestIncorrect > incorrectResult ? incorrectResult : lowestIncorrect;
                    highestIncorrect = highestIncorrect < incorrectResult ? incorrectResult : highestIncorrect;
                } else {
                    Assert.assertEquals(0, result.getPercents(i));
                }
            }
            int correctResult = result.getPercents(allAnswers.size() - 1);
            Assert.assertTrue(correctResult >= correctMin && correctResult <= correctMax);
            lowestCorrect = lowestCorrect > correctResult ? correctResult : lowestCorrect;
            highestCorrect = highestCorrect < correctResult ? correctResult : highestCorrect;
        }
        System.out.println(possibleAnswers.size() + " answers " + difficulty + " min/Max incorrect: <" + lowestIncorrect + "," + highestIncorrect + ">, " +
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
