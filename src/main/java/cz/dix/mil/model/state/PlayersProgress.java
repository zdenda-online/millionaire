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
     * Player is no longer playing (answered incorrectly, ended game or answered top question).
     */
    AFTER_GAME
}
