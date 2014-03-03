package cz.dix.mil.cmd;

/**
 * Represents settings of the game.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameSettings {

    private boolean isAutomaticAudience = true;

    public boolean isAutomaticAudience() {
        return isAutomaticAudience;
    }

    public void setAutomaticAudience(boolean automaticAudience) {
        this.isAutomaticAudience = automaticAudience;
    }
}
