package cz.dix.mil.model.runtime;

import cz.dix.mil.model.game.Answer;

/**
 * Represents results of automatic phone friend help.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class PhoneFriendResult {

    public enum Assurance {
        VERY_SURE, ALMOST_SURE, NOT_SURE, JUST_GUESS
    }

    private final Answer selectedAnswer;
    private final Assurance assurance;

    /**
     * Creates a new phone friend result.
     *
     * @param selectedAnswer selected answer by phone friend (can be null which means he didn't choose any)
     * @param assurance      assurance with which phone friend thinks selected answer is correct
     */
    public PhoneFriendResult(Answer selectedAnswer, Assurance assurance) {
        this.selectedAnswer = selectedAnswer;
        this.assurance = assurance;
    }

    /**
     * Gets a selected answer by phone friend. Can be null which means that he/she didn't choose any.
     *
     * @return selected answer by phone friend
     */
    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    /**
     * Gets an assurance with which phone friend thinks that answer is correct.
     *
     * @return assurance of answer
     */
    public Assurance getAssurance() {
        return assurance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Answer: " + ((selectedAnswer == null) ? "none" : selectedAnswer.isCorrect() ? "correct" : "incorrect")
                + ", Assurance: " + assurance;
    }
}
