package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

/**
 * Represents a sub-command for adding a player to a game in the game engine.
 */
public class AddGamePlayerSubCommand extends SubCommand {

    private final GameEngineController d_gameEngineController;

    /**
     * Constructs an AddGamePlayerSubCommand with the specified options and game engine controller.
     *
     * @param options              The options for adding a player. Expected length is 1.
     * @param p_gameEngineController The game engine controller to use for adding the player.
     * @throws InvalidCommandException if the options length is not 1.
     */
    public AddGamePlayerSubCommand(String[] options, GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.d_Options = options;
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the AddGamePlayerSubCommand by adding a player to the game using the game engine controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        PlayerDto dto = new PlayerDto();
        dto.setPlayerName(this.d_Options[0]);
        return d_gameEngineController.addPlayer(dto);
    }
}




