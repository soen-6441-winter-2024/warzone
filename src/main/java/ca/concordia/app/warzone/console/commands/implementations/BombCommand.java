package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the deploy command to deploy armies to a country.
 */
@Component
public class BombCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructor for BombCommand.
     * @param p_gameEngineController The game engine controller
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public BombCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the deploy command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("expected countryId argument");
        }

        String targetCountryId = p_subCommandsAndOptions[0];

        return d_gameEngineController.bomb(targetCountryId);
    }
}
