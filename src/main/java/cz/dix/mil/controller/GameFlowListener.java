package cz.dix.mil.controller;

/**
 * Listener for standard events of game flow.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface GameFlowListener {

    /**
     * Callback whether game is started (first question should be asked).
     */
    void onGameStart();

    /**
     * Callback when new question should be asked.
     */
    void onNewQuestion();
}
