package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the deploy command to deploy armies to a country.
 */
@Component
public class DeployCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructor for DeployCommand.
     * @param p_gameEngineController The game engine controller
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public DeployCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the deploy command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 2) {
            throw new InvalidCommandException("expected 2 arguments");
        }

        String countryId = p_subCommandsAndOptions[0];
        String reinforcementArmiesStr = p_subCommandsAndOptions[1];
        int reinforcementArmies = 0;
        try {
            reinforcementArmies = Integer.parseInt(reinforcementArmiesStr);
        }catch (NumberFormatException e) {
            throw new InvalidCommandException("reinforcement armies param not a valid number");
        }

        d_gameEngineController.deploy(countryId, reinforcementArmies);

        return null;
    }
}
