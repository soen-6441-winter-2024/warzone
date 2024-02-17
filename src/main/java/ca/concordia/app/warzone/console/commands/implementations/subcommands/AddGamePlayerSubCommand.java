package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

public class AddGamePlayerSubCommand extends SubCommand {

    private final GameEngineController gameEngineController;

    public AddGamePlayerSubCommand(String[] options, GameEngineController gameEngineController) throws InvalidCommandException {
        this.type = SubCommandType.ADD;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
        this.gameEngineController = gameEngineController;
    }

    @Override
    public String run() {
        // Call AddContinent from the service class
        PlayerDto dto = new PlayerDto();
        dto.setD_playerName(this.options[0]);
        String response = gameEngineController.addPlayer(dto);
        System.out.println(response);
        System.out.println("Adding a Game Player with name: " + this.options[0]);
        return gameEngineController.addPlayer(dto);
    }
}



