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

    private CommandFactory commandFactory;

    public ConsoleController(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the game \n\n");

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
                String[] optionsOrSubCommands = this.getOptions(fullCommand);
                command.run(optionsOrSubCommands);
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
    }/**/
}
