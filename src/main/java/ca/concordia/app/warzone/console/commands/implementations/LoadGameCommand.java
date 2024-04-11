package ca.concordia.app.warzone.console.commands.implementations;

import org.springframework.stereotype.Component;
import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

/**
 * Represents a command to load game from game file.
 */
@Component
public class LoadGameCommand extends Command {
    /** The controller for map editing. */
    private final GameEngineController d_gameEngineController;

    /**
     * Constructs a LoadGameCommand object.
     *
     * @param p_gameEngineController The game engine controller.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public LoadGameCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the load game command.
     *
     * @param p_options The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_options) {
        if (p_options.length != 1) {
            throw new InvalidCommandException("expected 1 filename");
        }

        return d_gameEngineController.loadGame(p_options[0]);
    }
}
