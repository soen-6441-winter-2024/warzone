package ca.concordia.app.warzone.console.commands.implementations;

import org.springframework.stereotype.Component;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

@Component
public class OrdersCompletedCommand extends Command {
    private final GameEngineController d_gameEngineController;

    /**
     * Constructor for OrdersCompleteCommand.
     * @param p_gameEngineController the controller to call on each execution
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public OrdersCompletedCommand(GameEngineController p_gameEngineController) throws InvalidCommandException {
        this.d_gameEngineController = p_gameEngineController;
    }

    /**
     * Runs the method which notifies the GameEngine that a player is done giving all their orders for the current round.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions){
        try {
            String result = d_gameEngineController.turnOrdersComplete();
            
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Something went wrong assigning countries";
        }
        
    }
}
