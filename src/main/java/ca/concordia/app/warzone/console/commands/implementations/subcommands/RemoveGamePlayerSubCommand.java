package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

/**
 * Represents a sub-command for removing a player from a game in the game engine.
 */
public class RemoveGamePlayerSubCommand extends SubCommand {

    private final GameEngineController d_gameEngineController;

    /**
     * Constructs a RemoveGamePlayerSubCommand with the specified options and game engine controller.
     *
     * @param options              The options for removing a player. Expected length is 1.
     * @param p_gameEngineController The game engine controller to use for removing the player.
     * @throws InvalidCommandException if the options length is not 1.
     */
    public RemoveGamePlayerSubCommand(String[] options, GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_Type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.d_Options = options;
        this.d_gameEngineController = p_gameEngineController;
    }

    /*
     * TODO Add remove player logic
     */

    /**
     * Runs the RemoveGamePlayerSubCommand by removing a player from the game using the game engine controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call RemovePlayer from the service class
        String playerName = this.d_Options[0];
        String response = d_gameEngineController.removePlayer(playerName);
        return response;
    }
}

