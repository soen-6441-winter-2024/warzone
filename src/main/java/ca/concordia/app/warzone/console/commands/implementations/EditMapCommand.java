package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Represents a command to edit the game map.
 */
@Component
public class EditMapCommand extends Command {

    /** The controller for the game engine. */
    private GameEngineController d_GameEngineController;

    /**
     * Constructs an EditMapCommand object.
     *
     * @param p_gameEngineController The GameEngineController instance.
     */
    @Autowired
    public EditMapCommand(GameEngineController p_gameEngineController) {
        this.d_GameEngineController = p_gameEngineController;
    }

    /**
     * Runs the edit map command.
     *
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     * @throws InvalidCommandException if the command is invalid.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) throws InvalidCommandException {
        System.out.println("Editing map");
        if (p_subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("filename required");
        }

        String filename = p_subCommandsAndOptions[0];
        return this.d_GameEngineController.editMap(filename);
    }
}
