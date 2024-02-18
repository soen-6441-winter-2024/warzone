package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EditMapCommand extends Command {
    private GameEngineController gameEngineController;

    public EditMapCommand(GameEngineController gameEngineController) {
        this.gameEngineController = gameEngineController;
    }

    @Override
    public String run(String[] subCommandsAndOptions) throws InvalidCommandException {
        System.out.println("Editing map");
        if(subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("filename required");
        }

        String filename = subCommandsAndOptions[0];
        return this.gameEngineController.editMap(filename);
    }
}
