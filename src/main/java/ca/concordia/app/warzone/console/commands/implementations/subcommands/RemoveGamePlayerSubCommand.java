package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

public class RemoveGamePlayerSubCommand extends SubCommand {

    private  GameEngineController gameEngineController;
    public RemoveGamePlayerSubCommand(String[] options, GameEngineController gameEngineController) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
        this.gameEngineController = gameEngineController;
    }

    /*
    TODO Add remove player logic
     */

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        String playerName = this.options[0];
        String response = gameEngineController.removePlayer(playerName);
        return response;
    }
}
