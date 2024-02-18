package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

@Component
public class NextPhaseCommand extends Command {

    private final GameEngineController controller;


    public NextPhaseCommand(GameEngineController controller) {
        this.controller = controller;
    }

    @Override
    public String run(String[] subCommandsAndOptions) {

//        return controller.nextPhase();
        return null;
    }
}
