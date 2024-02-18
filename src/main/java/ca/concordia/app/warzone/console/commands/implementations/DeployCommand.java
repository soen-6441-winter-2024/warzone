package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

/**
 * Represents the deploy command to deploy armies to a country.
 */
public class DeployCommand extends Command {

    private final String d_CountryID;
    private final String d_NumArmies;

    /**
     * Constructor for DeployCommand.
     * @param p_options The options for the deploy command.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public DeployCommand(String[] p_options) throws InvalidCommandException {
        if(p_options.length != 2) {
            throw new InvalidCommandException("expected 2 arguments");
        }

        this.d_Options = p_options;
        this.d_CountryID = p_options[0];
        this.d_NumArmies = p_options[1];
    }

    /**
     * Runs the deploy command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        System.out.println("Deploying the Country: " + this.d_CountryID + " with # armies " + this.d_NumArmies);
        return null;
    }
}
