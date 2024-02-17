package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class RemoveNeighborSubCommand extends SubCommand {
    public RemoveNeighborSubCommand(String[] options) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
    }

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        System.out.println("Removing a neighbor with values: " + this.options[0] + " " + this.options[1]);
        return null;
    }
}
