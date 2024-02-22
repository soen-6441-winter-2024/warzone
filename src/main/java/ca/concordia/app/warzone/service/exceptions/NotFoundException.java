package ca.concordia.app.warzone.service.exceptions;

/**
 * Exception class when a resource is not found
 */
public class NotFoundException extends Exception {

    /**
     * Creates a new exception
     *
     * @param name the resource name
     */
    public NotFoundException(String name) {
        super("not found: " + name);
    }
}
