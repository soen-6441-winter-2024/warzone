package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the blockade command to blockade a country.
 */
@Component
public class BlockadeCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructs a new blockade command with the specified game engine controller.
     *
     * @param p_gameEngineController The game engine controller.
     */
    public BlockadeCommand(GameEngineController p_gameEngineController) {
        d_gameEngineController = p_gameEngineController;
    }

    /**
     * Executes the blockade command to blockade a country.
     *
     * @param p_subCommandsAndOptions The array containing the command arguments.
     * @return A message indicating the success or failure of the command.
     * @throws InvalidCommandException If the command is invalid or the arguments are incorrect.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("expected 1 argument");
        }

        String country = p_subCommandsAndOptions[0];

        return  this.d_gameEngineController.blockade(country);
    }
}
