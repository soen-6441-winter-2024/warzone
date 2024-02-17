package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class ShowMapCommand extends Command {

    public ShowMapCommand() {
        this.type = CommandType.SHOW_MAP;
    }

    @Override
    public String run(String[] subCommandsAndOptions) {
        System.out.println("Showing a map!");

        return null;
    }
}
