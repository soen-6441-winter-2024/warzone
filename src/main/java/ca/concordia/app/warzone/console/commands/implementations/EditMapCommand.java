package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class EditMapCommand extends Command {
    private final String filename;
    public EditMapCommand(String[] options) throws InvalidCommandException {
        if(options.length != 1) {
            throw new InvalidCommandException("expected 1 filename");
        }

        this.options = options;
        this.filename = options[0];
    }

    @Override
    public void run() {
//        Opens the file and loads it, entering the "map editor" phase
        System.out.println("Opening file: " + this.filename);
    }
}
