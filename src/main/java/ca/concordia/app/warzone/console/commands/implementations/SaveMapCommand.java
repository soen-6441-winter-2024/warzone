package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class SaveMapCommand extends Command {
    private final String filename;
    public SaveMapCommand(String[] options) throws InvalidCommandException {
        if(options.length != 1) {
            throw new InvalidCommandException("expected 1 filename");
        }

        this.options = options;
        this.filename = options[0];
    }

    @Override
    public void run(String[] subCommandsAndOptions) {
//        Saves the current map being edited
        System.out.println("Saving file: " + this.filename);
    }
}
