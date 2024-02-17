package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class AddGamePlayerSubCommand extends SubCommand {

    public AddGamePlayerSubCommand(String[] options) throws InvalidCommandException {
        this.type = SubCommandType.ADD;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
    }

    @Override
    public String run() {
        // Call AddContinent from the service class
        System.out.println("Adding a Game Player with name: " + this.options[0]);
        return null;
    }
}
