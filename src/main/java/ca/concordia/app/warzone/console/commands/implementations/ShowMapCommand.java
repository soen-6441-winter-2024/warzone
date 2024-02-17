package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.AddNeighborSubCommand;
import ca.concordia.app.warzone.console.commands.implementations.subcommands.RemoveNeighborSubCommand;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ShowMapCommand extends Command {

    private MapEditorController controller;

    public ShowMapCommand(MapEditorController controller) {
        this.controller = controller;
        init();
    }
    private void init() {
        this.type = CommandType.SHOW_MAP;
    }

    @Override
    public String run(String[] subCommandsAndOptions) {
        System.out.println("Showing Map " );
        return "";
    }
}
