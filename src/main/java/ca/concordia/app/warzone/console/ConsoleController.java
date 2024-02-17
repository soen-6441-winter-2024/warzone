package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandFactory;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;


@Component
public class ConsoleController implements CommandLineRunner {

    public static final String INITIAL_PAGE = """
        Welcome to the warzone game \n
        
        First, let's start editing a map
        
        type 'help' to show available commands
        """;
    private final CommandFactory commandFactory;

    public ConsoleController(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(INITIAL_PAGE);

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
                Command command = this.getCommand(fullCommand);
                String[] options = this.getOptions(fullCommand);
                System.out.println(command.run(options));
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command provided: " + e.getMessage());
            }

        } while (!"quit".equals(fullCommand));
    }

    private String[] getOptions(String fullCommand) {
        String[] splittedFullCommand = fullCommand.split(" ");
        return Arrays.copyOfRange(splittedFullCommand, 1, splittedFullCommand.length);
    }

    private Command getCommand(String fullCommand) throws InvalidCommandException {
        String[] splittedFullCommand = fullCommand.split(" ");
        String firstCommand = splittedFullCommand[0];
        return commandFactory.newCommand(firstCommand);
    }
}
