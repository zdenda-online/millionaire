package cz.dix.mil.model;

import cz.dix.mil.model.game.Game;

/**
 * Represents settings of the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameSettings {

    private final Game game;
    private final boolean isRealAudience;

    /**
     * Creates a new settings.
     *
     * @param game           game to be played
     * @param isRealAudience flag whether game has real audience
     */
    public GameSettings(Game game, boolean isRealAudience) {
        this.game = game;
        this.isRealAudience = isRealAudience;
    }

    /**
     * Gets the game to be played.
     *
     * @return game to be played
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets a flag whether game has real audience.
     * Result of voting will not be generated automatically and moderator will must input results.
     *
     * @return true if real audience is on, otherwise false for automatic audience
     */
    public boolean isRealAudience() {
        return isRealAudience;
    }
}
