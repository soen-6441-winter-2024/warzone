package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the airlift command to attack or move army units across countries.
 */
@Component
public class AirliftCommand extends Command {
    private final GameEngineController d_gameEngineController;


    public AirliftCommand(GameEngineController p_GameEngineController) {
        d_gameEngineController = p_GameEngineController;
    }

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

        return d_gameEngineController.airlift(countryFrom, countryTo, armiesQuantity);
    }
}
