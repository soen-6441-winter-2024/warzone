package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the show phase command to display the current game phase.
 */
@Component
public class ShowPhaseCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructs a new show phase command with the specified game engine controller.
     *
     * @param p_gameEngineController The game engine controller.
     */
    public ShowPhaseCommand(GameEngineController p_gameEngineController) {
        d_gameEngineController = p_gameEngineController;
    }

    /**
     * Executes the show phase command to display the current game phase.
     *
     * @param p_SubCommandsAndOptions The array containing the command arguments.
     * @return The current game phase.
     */
    @Override
    public String run(String[] p_SubCommandsAndOptions) {
        return d_gameEngineController.getCurrentPhase();
    }
}
