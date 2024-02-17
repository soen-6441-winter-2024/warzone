package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

public class RemoveContinentSubCommand extends SubCommand {

    private GameEngineController gameEngineController;

    public RemoveContinentSubCommand(String[] options, GameEngineController gameEngineController) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
        this.gameEngineController = gameEngineController;
    }

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        System.out.println("Removing a continent with value: " + this.options[0]);

        return null;
    }
}
