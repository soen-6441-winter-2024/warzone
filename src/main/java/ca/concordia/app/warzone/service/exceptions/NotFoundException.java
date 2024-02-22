package ca.concordia.app.warzone.service.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String name) {
        super("not found: " + name);
    }
}
