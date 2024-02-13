package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePlayerCommand extends Command {

    final Pattern subCommandsPattern = Pattern.compile("-(add|remove)\\s(\\w+)\\s*(\\w*)", Pattern.CASE_INSENSITIVE);


    public GamePlayerCommand(String[] subCommandsAndOptions) throws InvalidCommandException {
        this.type = CommandType.GAME_PLAYER;

        String subCommands = Strings.join(Arrays.asList(subCommandsAndOptions), ' ');

        Matcher matcher = subCommandsPattern.matcher(subCommands);

        ArrayList<SubCommand> subCommandsArr = new ArrayList<>();


        while (matcher.find()) {
            if (matcher.group().equals(subCommands)) {
                continue;
            }

            String[] singleSubcommandAndOptions = matcher.group().split(" ");
            String singleSubCommand = singleSubcommandAndOptions[0].substring(1);

            if (singleSubCommand.equals(SubCommandType.ADD.toString())) {
                subCommandsArr.add(new AddGamePlayerSubCommand(Arrays.copyOfRange(singleSubcommandAndOptions, 1, singleSubcommandAndOptions.length)));
            } else if (singleSubCommand.equals(SubCommandType.REMOVE.toString())) {
                subCommandsArr.add(new RemoveGamePlayerSubCommand(Arrays.copyOfRange(singleSubcommandAndOptions, 1, singleSubcommandAndOptions.length)));
            }
        }

        if (subCommandsArr.isEmpty()) {
            throw new InvalidCommandException("at least once subcommand is required");
        }

        this.subCommands = subCommandsArr.toArray(new SubCommand[0]);
    }

    @Override
    public void run() {
        for (SubCommand subCommand : this.subCommands) {
            subCommand.run();
        }
    }
}
