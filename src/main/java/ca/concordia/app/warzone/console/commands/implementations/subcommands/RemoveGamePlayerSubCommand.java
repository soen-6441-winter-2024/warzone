package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class RemoveGamePlayerSubCommand extends SubCommand {
    public RemoveGamePlayerSubCommand(String[] options) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
    }

    @Override
    public void run() {
        // Call RemoveContinent from the service class
        System.out.println("Removing a Game Player with name: " + this.options[0]);
    }
}
