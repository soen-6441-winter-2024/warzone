package ca.concordia.app.warzone.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleController implements CommandLineRunner {

    private CommandDispatcher dispatcher;

    public ConsoleController(CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the game \n\n");

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.print("ssh>");
            command = scanner.nextLine();
            String result = dispatcher.exec(command);
            System.out.println(result);
        } while (!"quit".equals(command));

    }
}
