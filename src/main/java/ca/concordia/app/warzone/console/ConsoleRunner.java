package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandFactory;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ca.concordia.app.warzone.logging.LoggingService;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Controller for handling commands from the console.
 */
@Component
public class ConsoleRunner implements CommandLineRunner {

    /**
     * Initial page message displayed when the program starts.
     */
    public static final String INITIAL_PAGE = """
        Welcome to the Warzone game \n
        
        First, let's start editing a map
        
        type 'help' to show available commands
        """;

    private CommandDispatcher d_commandDispatcher;

    /**
     * Constructs a ConsoleController with the specified CommandFactory.
     *
     * @param p_commandDispatcher The CommandFactory to use.
     */
    public ConsoleRunner(CommandDispatcher p_commandDispatcher) {
        this.d_commandDispatcher = p_commandDispatcher;
    }

    /**
     * Runs the console controller.
     *
     * @param args The command-line arguments.
     * @throws Exception Throws if an error occurs while running.
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(INITIAL_PAGE);
        Scanner scanner = new Scanner(System.in);
        String fullCommand;

        do {
            System.out.print("> ");
            fullCommand = scanner.nextLine();
            d_commandDispatcher.executeLine(fullCommand);

        } while (!"quit".equals(fullCommand));
    }
}