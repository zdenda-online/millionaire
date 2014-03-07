package cz.dix.mil.model.state;

/**
 * Actual progress of the player in the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public enum PlayersProgress {

    /**
     * Game was not started yet (no question asked).
     */
    BEFORE_GAME,

    /**
     * Player is playing.
     */
    IN_GAME,

    /**
     * Player is no longer playing because he/she gave up
     */
    GAVE_UP,

    /**
     * Player is no longer playing because he/she answered incorrectly.
     */
    AFTER_INCORRECT_ANSWER,

    /**
     * Player is no longer playing because he/she answered all questions!
     */
    WON_GAME
}
