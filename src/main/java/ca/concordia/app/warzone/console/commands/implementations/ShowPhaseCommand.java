package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

@Component
public class ShowPhaseCommand extends Command {
    private final GameEngineController d_gameEngineController;

    public ShowPhaseCommand(GameEngineController p_gameEngineController) {
        d_gameEngineController = p_gameEngineController;
    }

    @Override
    public String run(String[] p_SubCommandsAndOptions) {
        return d_gameEngineController.getCurrentPhase();
    }
}
