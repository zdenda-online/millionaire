package cz.dix.mil.model.game.validation;

import cz.dix.mil.model.game.Game;

/**
 * Represents a validation of the game.
 * Each implementation of this interface can define its own rules for valid game instance.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 * @see AbstractGameValidation
 * @see OriginalGameValidation
 */
public interface GameValidation {

    /**
     * Validates given game with rules corresponding to this instance.
     *
     * @param game game to be validated
     * @return result of validation
     */
    ValidationResult validate(Game game);

}
