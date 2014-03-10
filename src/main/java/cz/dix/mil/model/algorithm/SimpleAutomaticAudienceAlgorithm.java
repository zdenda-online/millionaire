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
                    // incorrect 0 - 20 => avg. 10 | correct: 40 - 100 => avg. 70
                    res = generate(answers, 0, 20);
                } else { // answersCount == 2
                    // incorrect 0 - 30 => avg. 15 | correct: 80 - 100 => avg. 90
                    res = generate(answers, 0, 30);
                }
                break;
            case MID:
                if (answersCount == 4) {
                    // incorrect 10 - 26 => avg. 18 | correct: 22 - 70 => avg. 46
                    res = generate(answers, 10, 26);
                } else { // answersCount == 2
                    // incorrect 20 - 55 => avg. 37.5 | correct: 45 - 80 => avg. 62.5
                    res = generate(answers, 20, 55);
                }
                break;
            case HARD:
                if (answersCount == 4) {
                    // incorrect 19 - 27 => avg. 23 | correct: 19 - 43 => avg. 31
                    res = generate(answers, 19, 27);
                } else { // answersCount == 2
                    // incorrect 35 - 55 => avg. 45 | correct: 45 - 65 => avg. 55
                    res = generate(answers, 35, 55);
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
                int randomAdd = random.nextInt(incorrectMax - incorrectMin + 1);
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
