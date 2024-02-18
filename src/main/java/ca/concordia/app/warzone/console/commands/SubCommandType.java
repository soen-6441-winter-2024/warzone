package ca.concordia.app.warzone.console.commands;

/**
 * Enum representing different types of subcommands.
 */
public enum SubCommandType {

    /** Represents the "add" subcommand. */
    ADD("add"),

    /** Represents the "remove" subcommand. */
    REMOVE("remove");

    /** The text representation of the subcommand type. */
    private final String d_Text;

    /**
     * Constructor for SubCommandType.
     *
     * @param text The text representation of the subcommand type.
     */
    SubCommandType(final String text) {
        this.d_Text = text;
    }

    /**
     * Returns the text representation of the subcommand type.
     *
     * @return The text representation of the subcommand type.
     */
    @Override
    public String toString() {
        return d_Text;
    }
}
