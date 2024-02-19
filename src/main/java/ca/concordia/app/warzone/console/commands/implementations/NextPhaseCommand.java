package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

@Component
public class NextPhaseCommand extends Command {

    private GameEngineController controller;

    public NextPhaseCommand(GameEngineController controller) {
        this.controller = controller;
    }

    /**
     * Runs the next phase command
     *
     * @param subCommandsAndOptions The subcommands and options provided.
     * @returnn
     */
    @Override
    public String run(String[] subCommandsAndOptions) {
        return controller.nextPhase();
    }
}
