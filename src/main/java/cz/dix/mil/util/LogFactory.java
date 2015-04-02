package cz.dix.mil.util;

/**
 * Factory that provides current logging implementation.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class LogFactory {

    /**
     * Creates a new {@link Log} instance.
     *
     * @return log instance
     */
    public static Log instance() {
        return new SystemOutputLog();
    }
}
