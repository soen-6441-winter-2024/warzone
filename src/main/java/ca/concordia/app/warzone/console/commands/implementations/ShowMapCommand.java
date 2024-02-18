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

/**
 * Represents a command to show the map.
 */
@Component
public class ShowMapCommand extends Command {

    /** The controller for map editing. */
    private MapEditorController d_Controller;

    /**
     * Constructs a ShowMapCommand object.
     *
     * @param p_controller The controller for map editing.
     */
    public ShowMapCommand(MapEditorController p_controller) {
        this.d_Controller = p_controller;
        init();
    }

    /**
     * Initializes the command type.
     */
    private void init() {
        this.d_Type = CommandType.SHOW_MAP;
    }

    /**
     * Runs the show map command.
     *
     * @param p_subCommandsAndOptions The subcommands and options for showing the map.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        System.out.println("Showing Map");
        return "";
    }
}
