package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

@Component
public class BlockadeCommand extends Command {
    private final GameEngineController d_gameEngineController;

    public BlockadeCommand(GameEngineController p_gameEngineController) {
        d_gameEngineController = p_gameEngineController;
    }


    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("expected 1 argument");
        }

        String country = p_subCommandsAndOptions[0];

        return  this.d_gameEngineController.blockade(country);
    }
}
