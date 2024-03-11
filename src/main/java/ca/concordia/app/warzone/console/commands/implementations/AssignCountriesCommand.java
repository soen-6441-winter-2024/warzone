package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

/**
 * Command to assign countries to players in the game.
 */
@Component
public class AssignCountriesCommand extends Command {

    private final GameEngineController d_gameEngineController;

    /**
     * Constructor for AssignCountriesCommand.
     * @param p_gameEngineController the controller to call on each execution
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public AssignCountriesCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the assign countries command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions){
        try {
            d_gameEngineController.assignCountries();
        } catch (Exception e) {
            return "Something went wrong assigning countries";
        }

        return "Countries assigned";
    }



}
