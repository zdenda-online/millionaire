package cz.dix.mil.model.game;

/**
 * Wrapper for exceptions that are thrown when game cannot be created (serialized/deserialized to/from XML).
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameCreationException extends RuntimeException {

    public GameCreationException(String message) {
        super(message);
    }
}
