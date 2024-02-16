package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class AssignCountriesCommand extends Command {

    public AssignCountriesCommand(String[] options) throws InvalidCommandException {
        if(options.length >= 1) {
            throw new InvalidCommandException("no arguments expected");
        }

    }

    @Override
    public void run(String[] subCommandsAndOptions) {
//        Saves the current map being edited
        System.out.println("Loading Map file: ");
    }
}
