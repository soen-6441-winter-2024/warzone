package ca.concordia.app.warzone.console.exceptions;

/**
 * Represents an exception thrown when an invalid command is encountered.
 */
public class InvalidCommandException extends RuntimeException {

    /**
     * Constructs an InvalidCommandException with the specified detail message.
     *
     * @param p_message The detail message.
     */
    public InvalidCommandException(String p_message) {
        super(p_message);
    }
}
