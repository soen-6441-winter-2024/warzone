package ca.concordia.app.warzone.console.commands;

public enum SubCommandType {
    ADD("add"),
    REMOVE("remove"),
    ;

    private final String text;

    SubCommandType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
