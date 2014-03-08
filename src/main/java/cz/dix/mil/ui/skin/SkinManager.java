package cz.dix.mil.ui.skin;

/**
 * Static class that holds actual setting of the {@link Skin}.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SkinManager {

    private static Skin skin;

    /**
     * Sets actual skin of the application.
     * Note that you should recreate whole UI to take effect.
     *
     * @param skin skin to be set
     */
    public static void setSkin(Skin skin) {
        SkinManager.skin = skin;
    }

    /**
     * Gets actual skin of the application.
     *
     * @return skin of the application
     */
    public static Skin getSkin() {
        return skin;
    }
}

