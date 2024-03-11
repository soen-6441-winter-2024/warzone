package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandFactory;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.Phase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Controller for handling commands from the console.
 */
@Component
public class ConsoleController implements CommandLineRunner {

    /**
     * PhaseRepository for fetching and setting the current game phase
     */
    private final PhaseRepository d_phaseRepository;

    /**
     * Initial page message displayed when the program starts.
     */
    public static final String INITIAL_PAGE = """
        Welcome to the Warzone game \n
        
        First, let's start editing a map
        
        type 'help' to show available commands
        """;

    private final CommandFactory d_commandFactory;

    /**
     * Constructs a ConsoleController with the specified CommandFactory.
     *
     * @param p_commandFactory The CommandFactory to use.
     * @param p_phaseRepository The repository to use
     */
    public ConsoleController(CommandFactory p_commandFactory, PhaseRepository p_phaseRepository) {
        this.d_commandFactory = p_commandFactory;
        this.d_phaseRepository = p_phaseRepository;
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
        this.d_phaseRepository.setPhase(Phase.MAP_EDITOR);
        Scanner scanner = new Scanner(System.in);
        String fullCommand;

        do {
            System.out.print("> ");
            fullCommand = scanner.nextLine();
            if (fullCommand.isEmpty()) {
                System.out.println("You must provide a command");
                continue;
            }

            try {
                Command command = getCommand(fullCommand);
                String[] options = getOptions(fullCommand);
                System.out.println(command.run(options));;
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command provided: " + e.getMessage());
            }

        } while (!"quit".equals(fullCommand));
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