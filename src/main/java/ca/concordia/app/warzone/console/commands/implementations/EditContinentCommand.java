package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.AddContinentSubCommand;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.RemoveContinentSubCommand;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EditContinentCommand extends Command {
    final Pattern subCommandsPattern = Pattern.compile("-(add|remove)\\s(\\w+)\\s*(\\w*)", Pattern.CASE_INSENSITIVE);

    private final MapEditorController controller;

    public EditContinentCommand(MapEditorController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        this.type = CommandType.EDIT_CONTINENT;
    }

    @Override
    public String run(String[] subCommandsAndOptions) {

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
                subCommandsArr.add(new AddContinentSubCommand(Arrays.copyOfRange(singleSubcommandAndOptions, 1, singleSubcommandAndOptions.length), controller));
            } else if (singleSubCommand.equals(SubCommandType.REMOVE.toString())) {
                subCommandsArr.add(new RemoveContinentSubCommand(Arrays.copyOfRange(singleSubcommandAndOptions, 1, singleSubcommandAndOptions.length), controller));
            }
        }

        if (subCommandsArr.isEmpty()) {
            throw new InvalidCommandException("at least once subcommand is required");
        }

        StringBuilder result = new StringBuilder();

        for (SubCommand subCommand : subCommandsArr) {
            result.append(subCommand.run()).append("\n");
        }

        return result.toString();
    }
}
