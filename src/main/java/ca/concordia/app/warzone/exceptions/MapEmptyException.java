package ca.concordia.app.warzone.exceptions;

/**
 * Exception thrown when the map is empty.
 */
public class MapEmptyException extends Exception {
    /**
     * Constructs a new MapEmptyException with the specified detail message.
     *
     * @param p_message the detail message.
     */
    public MapEmptyException(String p_message) {
        super(p_message);
    }
}
