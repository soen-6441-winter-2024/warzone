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
     * Constructs a new advance command with the specified game engine controller.
     *
     * @param p_GameEngineController The game engine controller.
     */
    public AdvanceCommand(GameEngineController p_GameEngineController) {
        d_gameEngineController = p_GameEngineController;
    }

    /**
     * Executes the advance command to move armies from one country to another.
     *
     * @param p_subCommandsAndOptions The array containing the command arguments.
     * @return A message indicating the success or failure of the command.
     * @throws InvalidCommandException If the command is invalid or the arguments are incorrect.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 3) {
            throw new InvalidCommandException("expected 2 arguments");
        }

        String countryFrom = p_subCommandsAndOptions[0];
        String countryTo = p_subCommandsAndOptions[1];
        String armiesQuantityStr = p_subCommandsAndOptions[2];
        int armiesQuantity = 0;
        try {
            armiesQuantity = Integer.parseInt(armiesQuantityStr);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("armies quantity should be a number");
        }

        return d_gameEngineController.advance(countryFrom, countryTo, armiesQuantity);
    }
}
