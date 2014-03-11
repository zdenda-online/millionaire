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
    private final boolean isRealPhoneFriend;
    private final boolean skipIntro;

    /**
     * Creates a new settings.
     *
     * @param game              game to be played
     * @param isRealAudience    flag whether game has real audience
     * @param isRealPhoneFriend flag whether player will use real phone friend
     * @param skipIntro         flag whether game intro should be skipped (true if yes)
     */
    public GameSettings(Game game, boolean isRealAudience, boolean isRealPhoneFriend, boolean skipIntro) {
        this.game = game;
        this.isRealAudience = isRealAudience;
        this.isRealPhoneFriend = isRealPhoneFriend;
        this.skipIntro = skipIntro;
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

    /**
     * Gets a flag whether player will use real phone friend.
     *
     * @return true if player will use real phone friend, otherwise false for automatic phone friend
     */
    public boolean isRealPhoneFriend() {
        return isRealPhoneFriend;
    }

    /**
     * Gets a flag whether game intro (welcome song) should be skipped.
     *
     * @return true if intro should be skipped, otherwise false
     */
    public boolean skipIntro() {
        return skipIntro;
    }
}
