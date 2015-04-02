package cz.dix.mil.util;

/**
 * Interface for logging messages what is happening in application.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public interface Log {

    /**
     * Logs information message.
     *
     * @param message message to be logged
     */
    void logInfo(String message);

    /**
     * Logs error message.
     *
     * @param message message to be logged
     */
    void logError(String message);
}
