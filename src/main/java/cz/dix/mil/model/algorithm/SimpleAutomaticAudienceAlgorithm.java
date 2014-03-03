package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.game.Question;
import cz.dix.mil.model.state.AudienceResult;
import cz.dix.mil.model.state.QuestionDifficulty;

import java.util.Random;

/**
 * Simple implementation of automatic audience.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SimpleAutomaticAudienceAlgorithm implements AutomaticAudienceAlgorithm {

    private final Random random = new Random();

    @Override
    public AudienceResult count(Question question, QuestionDifficulty difficulty) {
        Integer[] res;
        switch (difficulty) {
            case EASY:
                // incorrect 0 - 25 => avg. 12,5, correct: 25 - 100 => avg. 62,5
                res = generate(question, 0, 30);
                break;
            case MID:
                // incorrect 7 - 27 => avg. 17, correct: 19 - 79 => avg. 49
                res = generate(question, 7, 27);
                break;
            case HARD:
                // incorrect 15 - 30 => avg. 22,5, correct: 10 - 55 => avg. 32,5
                res = generate(question, 15, 30);
                break;
            default:
                throw new RuntimeException("Unknown difficulty of question!");
        }
        return new AudienceResult(res[0], res[1], res[2], res[3]);
    }

    private Integer[] generate(Question question, int incorrectMin, int incorrectMax) {
        Integer[] res = new Integer[question.getAnswers().size()];
        int i = 0;
        int correctIdx = -1;
        for (Answer answer : question.getAnswers()) {
            if (answer.isCorrect()) {
                correctIdx = i;
            } else {
                int randomAdd = random.nextInt(incorrectMax - incorrectMin);
                res[i] = incorrectMin + randomAdd;
            }
            i++;
        }
        res[correctIdx] = rest(res);
        return res;
    }

    private int rest(Integer[] tempResults) {
        int result = 100;
        for (Integer tempResult : tempResults) {
            if (tempResult != null) {
                result -= tempResult;
            }
        }
        return result;
    }
}
