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

            Command command = null;
            try {
                command = this.getCommand(fullCommand);
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command provided: " + e.getMessage());
                continue;
            }

            command.run();
        } while (!"quit".equals(fullCommand));
    }

    private Command getCommand(String fullCommand) throws InvalidCommandException {
        String[] splittedFullCommand = fullCommand.split(" ");
        String firstCommand = splittedFullCommand[0];

        return CommandFactory.NewCommand(firstCommand, Arrays.copyOfRange(splittedFullCommand, 1, splittedFullCommand.length));
    }
}
