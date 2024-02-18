package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents a command to load a map from a file.
 */
@Component
public class LoadMapCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructs a LoadMapCommand object.
     *
     * @param p_options The options for loading the map.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public LoadMapCommand(GameEngineController p_gameEngineController) {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the load map command.
     *
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions)  throws InvalidCommandException {
        if(p_subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("exactly one filename required");
        }

        String fileName = p_subCommandsAndOptions[0];
        this.d_gameEngineController.loadMap(fileName);
        return null;
    }
}
