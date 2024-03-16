package ca.concordia.app.warzone.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Component that implements the runner to handle commands from the console.
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

    /**
     * Dispatcher component to handle full commands from the console
     */
    private final CommandDispatcher d_commandDispatcher;

    /**
     * Constructs a ConsoleController with the specified Command Dispatcher.
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