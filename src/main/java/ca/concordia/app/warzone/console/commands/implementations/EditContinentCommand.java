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

/**
 * Class to handle the EditContinent command.
 */
@Component
public class EditContinentCommand extends Command {
    // Pattern to match subcommands
    private final Pattern d_SubCommandsPattern = Pattern.compile("-(add|remove)\\s(\\w+)\\s*(\\w*)", Pattern.CASE_INSENSITIVE);

    // Controller for map editing
    private final MapEditorController d_Controller;

    /**
     * Constructor for EditContinentCommand.
     *
     * @param p_Controller The MapEditorController instance.
     */
    public EditContinentCommand(MapEditorController p_Controller) {
        this.d_Controller = p_Controller;
        init();
    }

    /**
     * Initializes the command.
     */
    private void init() {
        this.d_Type = CommandType.EDIT_CONTINENT;
    }

    /**
     * Runs the EditContinentCommand.
     *
     * @param p_SubCommandsAndOptions Subcommands and options provided.
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
                l_SubCommandsArr.add(new AddContinentSubCommand(Arrays.copyOfRange(l_SingleSubcommandAndOptions, 1, l_SingleSubcommandAndOptions.length), d_Controller));
            } else if (l_SingleSubCommand.equals(SubCommandType.REMOVE.toString())) {
                l_SubCommandsArr.add(new RemoveContinentSubCommand(Arrays.copyOfRange(l_SingleSubcommandAndOptions, 1, l_SingleSubcommandAndOptions.length), d_Controller));
            }
        }

        if (l_SubCommandsArr.isEmpty()) {
            throw new InvalidCommandException("At least one subcommand is required");
        }

        StringBuilder l_Result = new StringBuilder();

        for (SubCommand l_SubCommand : l_SubCommandsArr) {
            l_Result.append(l_SubCommand.run()).append("\n");
        }

        return l_Result.toString();
    }
}