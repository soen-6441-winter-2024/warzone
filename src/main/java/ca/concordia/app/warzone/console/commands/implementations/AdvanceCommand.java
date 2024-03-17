package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the advance command to attack or move army units across countries.
 */
@Component
public class AdvanceCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructor for AdvanceCommand.
     * @param p_gameEngineController The game engine controller
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public AdvanceCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the advance command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 3) {
            throw new InvalidCommandException("expected 3 argument");
        }

        String countryFrom = p_subCommandsAndOptions[0];
        String countryTo = p_subCommandsAndOptions[1];
        int numOfArmies = 0;

        try {
            numOfArmies = Integer.parseInt(p_subCommandsAndOptions[2]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("number of armies param not a valid number");
        }

        return d_gameEngineController.advance();
    }
}
