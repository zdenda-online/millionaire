package cz.dix.mil.controller;

import cz.dix.mil.model.game.Answer;

/**
 * Listener for events when player answers.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface AnswerListener {

    /**
     * Callback when player selects an answer.
     *
     * @param answer selected answer by player
     */
    void onAnswerSelected(Answer answer);

    /**
     * Callback when answers are revealed (by moderator).
     */
    void onAnswersReveal();
}
