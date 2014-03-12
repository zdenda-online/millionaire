package cz.dix.mil.model.algorithm;

import cz.dix.mil.model.game.Answer;
import cz.dix.mil.model.runtime.PhoneFriendResult;
import cz.dix.mil.model.runtime.QuestionDifficulty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Default implementation of automatic phone friend.
 * <p/>
 * It firstly chooses answer (correct or incorrect) according to the difficulty. Then if any answer is chosen, the
 * assurance is chosen for it. The assurance is also influenced by fact if chosen answer is correct or not.
 * The chances of selection correct answer and generation of various assurances are defined to return fairly
 * reasonable results.
 * <p/>
 * Note that it can be used only for classical type of game where every question has 4 answers.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class DefaultAutomaticPhoneFriend implements AutomaticPhoneFriend {

    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public PhoneFriendResult call(List<Answer> possibleAnswers, QuestionDifficulty difficulty) {
        int answersCount = possibleAnswers.size();

        Answer answer;
        PhoneFriendResult.Assurance assurance;
        switch (difficulty) {
            case EASY:
                if (answersCount == 4) {
                    answer = chooseAnswer(possibleAnswers, 5, 90);
                    assurance = (answer == null) ? null : // no answer was chosen, assurance is not needed
                            (answer.isCorrect()) ? chooseAssurance(90, 80, 70) : chooseAssurance(3, 10, 15);
                } else { // 2 answers
                    answer = chooseAnswer(possibleAnswers, 5, 95);
                    assurance = (answer == null) ? null : // no answer was chosen, assurance is not needed
                            (answer.isCorrect()) ? chooseAssurance(95, 85, 75) : chooseAssurance(3, 10, 15);
                }
                break;
            case MID:
                if (answersCount == 4) {
                    answer = chooseAnswer(possibleAnswers, 25, 65);
                    assurance = (answer == null) ? null : // no answer was chosen, assurance is not needed
                            (answer.isCorrect()) ? chooseAssurance(60, 60, 60) : chooseAssurance(4, 15, 25);
                } else { // 2 answers
                    answer = chooseAnswer(possibleAnswers, 20, 75);
                    assurance = (answer == null) ? null : // no answer was chosen, assurance is not needed
                            (answer.isCorrect()) ? chooseAssurance(65, 65, 65) : chooseAssurance(4, 15, 25);
                }
                break;
            case HARD:
                if (answersCount == 4) {
                    answer = chooseAnswer(possibleAnswers, 60, 55);
                    assurance = (answer == null) ? null : // no answer was chosen, assurance is not needed
                            (answer.isCorrect()) ? chooseAssurance(30, 40, 50) : chooseAssurance(5, 20, 30);
                } else { // 2 answers
                    answer = chooseAnswer(possibleAnswers, 40, 65);
                    assurance = (answer == null) ? null : // no answer was chosen, assurance is not needed
                            (answer.isCorrect()) ? chooseAssurance(35, 45, 55) : chooseAssurance(5, 20, 30);
                }
                break;
            default:
                throw new RuntimeException("Unknown difficulty of question!");
        }
        return new PhoneFriendResult(answer, assurance);
    }

    /**
     * Chooses any answer from possible ones according to given chances (probabilities).
     *
     * @param possibleAnswers      possible answers
     * @param chanceNotToChoose    chance that friend will not choose any answer
     * @param chanceToChoseCorrect chance that friend chooses correct one (in full percents)
     * @return selected answer
     */
    private Answer chooseAnswer(List<Answer> possibleAnswers, int chanceNotToChoose, int chanceToChoseCorrect) {
        if ((random.nextInt(100)) + 1 <= chanceNotToChoose) {
            return null; // he does not choose anything
        }
        if ((random.nextInt(100) + 1) <= chanceToChoseCorrect) {
            return findCorrectAnswer(possibleAnswers);
        } else {
            return randomlyChooseIncorrectAnswer(possibleAnswers);
        }
    }

    /**
     * Chooses an assurance according to given chances (probabilities). If any of chances does not succeed, assurance
     * will be just tip.
     *
     * @param chanceToBeVerySure   chance that friend will be very sure
     * @param chanceToBeAlmostSure chance that friend will be almost sure
     * @param chanceToBeNotSure    chance that friend will not be sure
     * @return chosen assurance
     */
    private PhoneFriendResult.Assurance chooseAssurance(int chanceToBeVerySure, int chanceToBeAlmostSure, int chanceToBeNotSure) {
        if ((random.nextInt(100)) + 1 <= chanceToBeVerySure) {
            return PhoneFriendResult.Assurance.VERY_SURE;
        }
        if ((random.nextInt(100)) + 1 <= chanceToBeAlmostSure) {
            return PhoneFriendResult.Assurance.ALMOST_SURE;
        }
        if ((random.nextInt(100)) + 1 <= chanceToBeNotSure) {
            return PhoneFriendResult.Assurance.NOT_SURE;
        }
        return PhoneFriendResult.Assurance.JUST_GUESS;
    }

    /**
     * Finds a correct answer in possible answers.
     *
     * @param possibleAnswers possible answers
     * @return correct answer
     */
    private Answer findCorrectAnswer(List<Answer> possibleAnswers) {
        for (Answer answer : possibleAnswers) {
            if (answer.isCorrect()) {
                return answer;
            }
        }
        throw new RuntimeException("None of possible answers is correct?!");
    }

    /**
     * Randomly chooses any incorrect answer from possible ones.
     *
     * @param possibleAnswers possible answers
     * @return random incorrect answer
     */
    private Answer randomlyChooseIncorrectAnswer(List<Answer> possibleAnswers) {
        List<Answer> copy = new ArrayList<>(possibleAnswers);
        Collections.shuffle(copy); // randomness
        for (Answer answer : copy) {
            if (!answer.isCorrect()) {
                return answer;
            }
        }
        throw new RuntimeException("None of possible answers is incorrect?!");
    }
}
