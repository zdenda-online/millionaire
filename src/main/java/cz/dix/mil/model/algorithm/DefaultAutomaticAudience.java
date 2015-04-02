package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.AudienceVotingResult;
import cz.dix.mil.model.runtime.QuestionDifficulty;
import cz.dix.mil.util.Log;
import cz.dix.mil.util.LogFactory;

import java.util.List;
import java.util.Random;

/**
 * Default implementation of automatic audience.
 * <p/>
 * It generates percents for possible answers by randomly computing values for incorrect answers and then assigning
 * rest to 100% to correct answer. The random values for incorrect answers are from predefined range which is different
 * for every question difficulty and for different count of possible answers (2 or 4). These ranges are defined
 * to return fairly reasonable results.
 * <p/>
 * Note that it can be used only for classical type of game where every question has 4 answers.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class DefaultAutomaticAudience implements AutomaticAudience {

    private static final Log LOG = LogFactory.instance();
    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public AudienceVotingResult vote(List<Answer> allAnswers, List<Answer> possibleAnswers, QuestionDifficulty difficulty) {
        int answersCount = possibleAnswers.size();
        if (answersCount != 2 && answersCount != 4) {
            throw new RuntimeException("Unable to vote for " + possibleAnswers.size() + " answers (only 2 and 4 are supported)");
        }

        int[] percents;
        switch (difficulty) {
            case EASY:
                if (answersCount == 4) {
                    // incorrect 0 - 20 => avg. 10 | correct: 40 - 100 => avg. 70
                    percents = generatePercents(possibleAnswers, 0, 20);
                } else { // answersCount == 2
                    // incorrect 0 - 30 => avg. 15 | correct: 80 - 100 => avg. 90
                    percents = generatePercents(possibleAnswers, 0, 30);
                }
                break;
            case MID:
                if (answersCount == 4) {
                    // incorrect 10 - 26 => avg. 18 | correct: 22 - 70 => avg. 46
                    percents = generatePercents(possibleAnswers, 10, 26);
                } else { // answersCount == 2
                    // incorrect 20 - 55 => avg. 37.5 | correct: 45 - 80 => avg. 62.5
                    percents = generatePercents(possibleAnswers, 20, 55);
                }
                break;
            case HARD:
                if (answersCount == 4) {
                    // incorrect 19 - 27 => avg. 23 | correct: 19 - 43 => avg. 31
                    percents = generatePercents(possibleAnswers, 19, 27);
                } else { // answersCount == 2
                    // incorrect 35 - 55 => avg. 45 | correct: 45 - 65 => avg. 55
                    percents = generatePercents(possibleAnswers, 35, 55);
                }
                break;
            default:
                throw new RuntimeException("Unknown difficulty of question!");
        }
        return mergeToResult(allAnswers, possibleAnswers, percents);
    }

    /**
     * Generates percents for possible answers.
     * Incorrect min/max values are used as interval from which will be randomly picked number (percents) for incorrect answer.
     * Correct question then computed as rest to 100%.
     *
     * @param possibleAnswers possible answers
     * @param incorrectMin    minimal value for incorrect answers
     * @param incorrectMax    maximal value for incorrect answers
     * @return percents for possible answer (in the same order as possible answers)
     */
    private int[] generatePercents(List<Answer> possibleAnswers, int incorrectMin, int incorrectMax) {
        int[] res = new int[possibleAnswers.size()];
        int i = 0;
        int correctIdx = -1;
        for (Answer answer : possibleAnswers) {
            if (answer.isCorrect()) {
                correctIdx = i;
            } else {
                int randomAdd = random.nextInt(incorrectMax - incorrectMin + 1);
                res[i] = incorrectMin + randomAdd;
            }
            i++;
        }
        return restTo100(res, correctIdx);
    }

    /**
     * Computes last value from partial results to be sure that sum of all results is equal to 100%.
     *
     * @param partialResult partial result
     * @param missingIndex  index of partial result that misses value
     * @return array that is same as partial result but missing value is filled with rest to 100%
     */
    private int[] restTo100(int[] partialResult, int missingIndex) {
        int rest = 100;
        for (int i = 0; i < partialResult.length; i++) {
            if (missingIndex != i) {
                rest -= partialResult[i];
            }
        }
        partialResult[missingIndex] = rest;
        return partialResult;
    }

    /**
     * Merges result of computations to the final {@link cz.dix.mil.model.runtime.AudienceVotingResult} instance.
     *
     * @param allAnswers      all answers to question
     * @param possibleAnswers possible answers to question
     * @param percents        computed percents for possible answers
     * @return merged result
     */
    private AudienceVotingResult mergeToResult(List<Answer> allAnswers, List<Answer> possibleAnswers, int[] percents) {
        int[] allPercents = new int[allAnswers.size()];
        int allIdx = 0;
        int possibleIdx = 0;
        for (Answer answer : allAnswers) {
            int p = possibleAnswers.contains(answer) ? percents[possibleIdx++] : 0;
            allPercents[allIdx++] = p;
        }
        return new AudienceVotingResult(allPercents);
    }
}
