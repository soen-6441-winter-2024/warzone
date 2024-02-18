package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

/**
 * Represents a command to load a map from a file.
 */
public class LoadMapCommand extends Command {
    /** The filename of the map. */
    private final String d_Filename;

    /**
     * Constructs a LoadMapCommand object.
     *
     * @param p_options The options for loading the map.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public LoadMapCommand(String[] p_options) throws InvalidCommandException {
        if(p_options.length != 1) {
            throw new InvalidCommandException("expected 1 filename");
        }

        this.d_Options = p_options;
        this.d_Filename = p_options[0];
    }

    /**
     * Runs the load map command.
     *
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        System.out.println("Loading Map file: " + this.d_Filename);
        return null;
    }
}
