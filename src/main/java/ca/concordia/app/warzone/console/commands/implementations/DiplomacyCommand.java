package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the diplomacy command to halt attacks between 2 players for a round.
 */
@Component
public class DiplomacyCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructor for DiplomacyCommand.
     * @param p_gameEngineController The game engine controller
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public DiplomacyCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the diplomacy command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 1) {
            throw new InvalidCommandException("expected TargetPlayer argument");
        }

        String targetPlayer = p_subCommandsAndOptions[0];

        return d_gameEngineController.diplomacy(targetPlayer);
    }
}
