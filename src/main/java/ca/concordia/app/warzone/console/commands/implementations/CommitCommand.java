package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.controller.GameEngineController;

public class CommitCommand extends Command {
    private final GameEngineController d_gameEngineController;

    public CommitCommand(GameEngineController dGameEngineController) {
        d_gameEngineController = dGameEngineController;
    }

    @Override
    public String run(String[] p_SubCommandsAndOptions) {
        return null;
    }
}
