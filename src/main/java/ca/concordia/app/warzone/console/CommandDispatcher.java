package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandFactory;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.logging.LoggingService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Dispatcher Component that receive full line commands from console and process them using the command factory
 */
@Component
public class CommandDispatcher {

    /**
     * Command factory to use
     */
    private final CommandFactory d_commandFactory;

    /**
     * Constructor of the dispatcher component
     *
     * @param d_commandFactory Command factory that will be attached to this component
     */
    public CommandDispatcher(CommandFactory d_commandFactory) {
        this.d_commandFactory = d_commandFactory;
    }

    /**
     * execute the full line received from the command line
     *
     * @param fullCommand: full line from command line
     */
    public void executeLine(String fullCommand) {

        try {

            if (fullCommand.isEmpty()) {
                LoggingService.log("Command is empty, You must provide a command");
                throw new InvalidCommandException("You must provide a command");
            }

            LoggingService.log("fullCommand: " + fullCommand);
            Command command = getCommand(fullCommand);
            String[] options = getOptions(fullCommand);
            System.out.println(command.run(options));
        } catch (InvalidCommandException e) {
            LoggingService.log("Exception: " + e.getMessage());
            System.out.println("Invalid command provided: " + e.getMessage());
        } catch (Exception e) {
            LoggingService.log("Exception: " + e.getMessage());
            System.out.println("General Exception during execution: " + e.getMessage());
        }
    }

    /**
     * Retrieves the command options from the full command string.
     *
     * @param p_fullCommand The full command string.
     * @return An array of command options.
     */
    private String[] getOptions(String p_fullCommand) {
        String[] splittedFullCommand = p_fullCommand.split(" ");
        return Arrays.copyOfRange(splittedFullCommand, 1, splittedFullCommand.length);
    }

    /**
     * Retrieves the command object from the full command string.
     *
     * @param p_fullCommand The full command string.
     * @return The Command object corresponding to the command string.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    private Command getCommand(String p_fullCommand) throws InvalidCommandException {
        String[] splittedFullCommand = p_fullCommand.split(" ");
        String firstCommand = splittedFullCommand[0];
        return d_commandFactory.newCommand(firstCommand);
    }

}
