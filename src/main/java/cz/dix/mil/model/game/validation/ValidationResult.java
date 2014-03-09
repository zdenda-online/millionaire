package cz.dix.mil.model.game.validation;

/**
 * Simple value object that represents results of the validation and possible message if not valid.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class ValidationResult {

    private final boolean isValid;
    private final String message;

    /**
     * Creates a new successful validation result.
     */
    public ValidationResult() {
        this(true, null);
    }

    /**
     * Creates a new failing validation result.
     *
     * @param message message why validation failed
     */
    public ValidationResult(String message) {
        this(false, message);
    }

    /**
     * Creates a new validation result.
     *
     * @param isValid flag whether validation succeeded (true if yes)
     * @param message message of tha validation (typically with error)
     */
    public ValidationResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    /**
     * Gets a flag whether validation is valid.
     *
     * @return true if object is valid, otherwise false
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Gets a message (typically when validation fails).
     *
     * @return validation message
     */
    public String getMessage() {
        return message;
    }
}
