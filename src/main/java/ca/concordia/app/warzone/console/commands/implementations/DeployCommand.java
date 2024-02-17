package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
public class DeployCommand extends Command {
    private final String l_countryID;
    private final String l_num;
    public DeployCommand(String[] options) throws InvalidCommandException {
        if(options.length != 2) {
            throw new InvalidCommandException("expected 2 arguments");
        }

        this.options = options;
        this.l_countryID = options[0];
        this.l_num = options[1];
    }

    @Override
    public String run(String[] subCommandsAndOptions) {
//        Saves the current map being edited
        System.out.println("Deploying the Country: " + this.l_countryID + " with # armies " + this.l_num);
        return null;
    }
}
