package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class AddCountrySubCommand extends SubCommand {
    public AddCountrySubCommand(String[] options) throws InvalidCommandException {
        this.type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.options = options;
    }

    @Override
    public void run() {
        // Call AddCountry from the service class
        System.out.println("Adding a country with values: " + this.options[0] + " " + this.options[1]);
    }
}
