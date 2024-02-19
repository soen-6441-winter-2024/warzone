package ca.concordia.app.warzone.console.commands;

/**
 * Abstract class representing a subcommand.
 */
public abstract class SubCommand {

    /**
     * Default subcommand
     */
    public SubCommand() {}

    /** The type of the subcommand. */
    protected SubCommandType d_Type;

    /** The options associated with the subcommand. */
    protected String[] d_Options;

    /**
     * Runs the subcommand.
     *
     * @return The result of executing the subcommand.
     */
    public abstract String run();
}
