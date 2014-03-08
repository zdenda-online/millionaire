package cz.dix.mil.model.game;

import cz.dix.mil.model.game.Game;

/**
 * Exception that states that validation of the {@link Game} instance failed.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class GameValidationException extends RuntimeException {

    public GameValidationException(String message) {
        super(message);
    }
}
