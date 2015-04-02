package cz.dix.mil.util;

/**
 * Log that prints everything to the standard/error output of the process.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class SystemOutputLog implements Log {

    @Override
    public void logInfo(String message) {
        System.out.println(message);
    }

    @Override
    public void logError(String message) {
        System.err.println(message);
    }
}
