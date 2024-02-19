package ca.concordia.app.warzone.exceptions;

/**
 * Exception thrown when the format of the map content is invalid.
 */
public class InvalidMapContentFormat extends Exception {
    /**
     * Constructs a new InvalidMapContentFormat with the specified detail message.
     *
     * @param p_message the detail message.
     */
    public InvalidMapContentFormat(String p_message) {
        super(p_message);
    }
}
