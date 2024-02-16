package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

public class RemoveCountrySubCommand extends SubCommand {

    private GameEngineController controller;
    public RemoveCountrySubCommand(String[] options, GameEngineController controller) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
        this.controller = controller;
    }

    @Override
    public void run() {
        // Call RemoveContinent from the service class
        System.out.println("Removing a country with value: " + this.options[0]);
        controller.removeCountry(this.options[0]);
    }
}
