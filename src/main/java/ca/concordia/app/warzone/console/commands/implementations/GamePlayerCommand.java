package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a command to manage game players.
 */
@Component
public class GamePlayerCommand extends Command {

    /**
     * The pattern for subcommands.
     */
    final Pattern d_SubCommandsPattern = Pattern.compile("-(add|remove)\\s(\\w+)\\s*(\\w*)", Pattern.CASE_INSENSITIVE);

    /**
     * The controller for the game engine.
     */
    private final GameEngineController d_Controller;

    /**
     * Constructs a GamePlayerCommand object.
     *
     * @param p_controller The GameEngineController instance.
     */
    public GamePlayerCommand(GameEngineController p_controller) {
        this.d_Controller = p_controller;
        init();
    }

    /**
     * Initializes the command type.
     */
    private void init() {
        this.d_Type = CommandType.GAME_PLAYER;
    }

    /**
     * Runs the game player command.
     *
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        String subCommands = Strings.join(Arrays.asList(p_subCommandsAndOptions), ' ');

        Matcher matcher = d_SubCommandsPattern.matcher(subCommands);

        ArrayList<SubCommand> subCommandArr = new ArrayList<>();

        while (matcher.find()) {
            String[] singleSubcommandAndOptions = matcher.group().split(" ");
            String singleSubCommand = singleSubcommandAndOptions[0].substring(1);

            if (singleSubCommand.equals(SubCommandType.ADD.toString())) {
                subCommandArr.add(new AddGamePlayerSubCommand(Arrays.copyOfRange(singleSubcommandAndOptions, 1, singleSubcommandAndOptions.length), d_Controller));
            } else if (singleSubCommand.equals(SubCommandType.REMOVE.toString())) {
                subCommandArr.add(new RemoveGamePlayerSubCommand(Arrays.copyOfRange(singleSubcommandAndOptions, 1, singleSubcommandAndOptions.length), d_Controller));
            }
        }

        if (subCommandArr.isEmpty()) {
            throw new InvalidCommandException("incorrect command format");
        }

        StringBuilder result = new StringBuilder();

        for (SubCommand subCommand : subCommandArr) {
            result.append(subCommand.run()).append("\n");
        }

        return result.toString();
    }
}