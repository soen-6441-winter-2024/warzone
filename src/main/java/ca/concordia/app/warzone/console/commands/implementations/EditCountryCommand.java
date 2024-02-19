package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.AddCountrySubCommand;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.RemoveCountrySubCommand;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command for editing countries in the map.
 */
@Component
public class EditCountryCommand extends Command {

    // Pattern for parsing subcommands
    private final Pattern d_SubCommandsPattern = Pattern.compile("-(add|remove)\\s(\\w+)\\s*(\\w*)", Pattern.CASE_INSENSITIVE);

    // Controller for map editing operations
    private final MapEditorController d_Controller;

    /**
     * Constructor for EditCountryCommand.
     * @param p_controller The MapEditorController instance.
     */
    public EditCountryCommand(MapEditorController p_controller) {
        this.d_Controller = p_controller;
        init();
    }

    /**
     * Initializes the command.
     */
    private void init() {
        this.d_Type = CommandType.EDIT_COUNTRY;
    }

    /**
     * Executes the edit country command.
     * @param p_SubCommandsAndOptions Array of subcommands and options.
     * @return Result of the command execution.
     */
    @Override
    public String run(String[] p_SubCommandsAndOptions) {

        String l_SubCommands = Strings.join(Arrays.asList(p_SubCommandsAndOptions), ' ');

        Matcher l_Matcher = d_SubCommandsPattern.matcher(l_SubCommands);

        ArrayList<SubCommand> l_SubCommandsArr = new ArrayList<>();

        while (l_Matcher.find()) {

            String[] l_SingleSubcommandAndOptions = l_Matcher.group().split(" ");
            String l_SingleSubCommand = l_SingleSubcommandAndOptions[0].substring(1);

            if (l_SingleSubCommand.equals(SubCommandType.ADD.toString())) {
                l_SubCommandsArr.add(new AddCountrySubCommand(Arrays.copyOfRange(l_SingleSubcommandAndOptions, 1, l_SingleSubcommandAndOptions.length), d_Controller ));
            }
            else if (l_SingleSubCommand.equals(SubCommandType.REMOVE.toString())) {
                l_SubCommandsArr.add(new RemoveCountrySubCommand(Arrays.copyOfRange(l_SingleSubcommandAndOptions, 1, l_SingleSubcommandAndOptions.length), d_Controller));
            }
        }

        if (l_SubCommandsArr.isEmpty()) {
            throw new InvalidCommandException("at least once subcommand is required");
        }

        StringBuilder l_Result = new StringBuilder();

        for (SubCommand subCommand : l_SubCommandsArr) {
            l_Result.append(subCommand.run()).append("\n");
        }

        return l_Result.toString();
    }
}
