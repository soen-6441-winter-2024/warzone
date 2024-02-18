package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

public class AddGamePlayerSubCommand extends SubCommand {

    private final GameEngineController gameEngineController;

    public AddGamePlayerSubCommand(String[] options, GameEngineController gameEngineController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.d_Options = options;
        this.gameEngineController = gameEngineController;
    }

    @Override
    public String run() {
        PlayerDto dto = new PlayerDto();
        dto.setPlayerName(this.d_Options[0]);
        return gameEngineController.addPlayer(dto);
    }
}



