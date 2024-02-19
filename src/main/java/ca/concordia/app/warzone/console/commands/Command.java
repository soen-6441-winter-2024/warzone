package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;
/**
 * Abstract class representing a command.
 */
public abstract class Command {

    /**
     * Default constructor
     */
    public Command() {

   }

    /**
     * Type of the command
     */
    protected CommandType d_Type;

    /**
     * Options associated with the command
     */
    protected String[] d_Options;

    /**
     * Subcommands associated with the command
     */
    protected SubCommand[] d_SubCommands;

    /**
     * Abstract method to run the command.
     * @param p_SubCommandsAndOptions The subcommands and options provided.
     * @return Result of the command execution.
     */
    public abstract String run(String[] p_SubCommandsAndOptions);
}
