package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.QuestionDifficulty;

import java.util.List;
import java.util.Random;

/**
 * Simple implementation of automatic audience.
 * Note that it can be used only for classical type of game where every question has 4 answers.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SimpleAutomaticAudienceAlgorithm implements AutomaticAudienceAlgorithm {

    private final Random random = new Random();

    @Override
    public int[] count(List<Answer> answers, QuestionDifficulty difficulty) {
        int answersCount = answers.size();
        if (answersCount != 2 && answersCount != 4) {
            throw new RuntimeException("Unable to count for " + answers.size() + " answers");
        }

        Integer[] res;
        switch (difficulty) {
            case EASY:
                if (answersCount == 4) {
                    // incorrect 0 - 20 => avg. 10 | correct: 60 - 100 => avg. 80
                    res = generate(answers, 0, 20);
                } else { // answersCount == 2
                    // incorrect 0 - 30 => avg. 15 | correct: 70 - 100 => avg. 85
                    res = generate(answers, 0, 30);
                }
                break;
            case MID:
                if (answersCount == 4) {
                    // incorrect 7 - 23 => avg. 15 | correct: 31 - 79 => avg. 55
                    res = generate(answers, 7, 23);
                } else { // answersCount == 2
                    // incorrect 20 - 40 => avg. 30 | correct: 60 - 80 => avg. 70
                    res = generate(answers, 20, 40);
                }
                break;
            case HARD:
                if (answersCount == 4) {
                    // incorrect 15 - 27 => avg. 21 | correct: 19 - 55 => avg. 37
                    res = generate(answers, 15, 27);
                } else { // answersCount == 2
                    // incorrect 30 - 50 => avg. 42.5 | correct: 45 - 70 => avg. 57.5
                    res = generate(answers, 30, 55);
                }
                break;
            default:
                throw new RuntimeException("Unknown difficulty of question!");
        }
        return toPrimitives(res);
    }

    private Integer[] generate(List<Answer> answers, int incorrectMin, int incorrectMax) {
        Integer[] res = new Integer[answers.size()];
        int i = 0;
        int correctIdx = -1;
        for (Answer answer : answers) {
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

    private int[] toPrimitives(Integer[] obj) {
        int[] out = new int[obj.length];
        int i = 0;
        for (Integer o : obj) {
            out[i++] = o;
        }
        return out;
    }
}
