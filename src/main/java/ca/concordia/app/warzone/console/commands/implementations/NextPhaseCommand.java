package ca.concordia.app.warzone.console.commands.implementations;
import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Command for going into the next phase of the game
 */
@Component
public class NextPhaseCommand extends Command {

    /**
     * The GameEngineController to use.
     */
    private GameEngineController controller;

    /**
     * Constructor
     * @param controller The GameEngineController to use.
     */
    public NextPhaseCommand(GameEngineController controller) {
        this.controller = controller;
    }

    /**
     * Runs the next phase command
     *
     * @param subCommandsAndOptions The subcommands and options provided.
     * @return the result of the command
     */
    @Override
    public String run(String[] subCommandsAndOptions) {
        return controller.nextPhase();
    }
}
