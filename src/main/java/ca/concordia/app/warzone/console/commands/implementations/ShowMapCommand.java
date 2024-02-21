package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.springframework.stereotype.Component;

/**
 * Represents a command to show the map.
 */
@Component
public class ShowMapCommand extends Command {

    /** The controller for map editing. */
    private final MapEditorController d_MapEditorController;

    /**
     * Constructs a ShowMapCommand object.
     *
     * @param p_MapEditorController The controller for map editing.
     */
    public ShowMapCommand(MapEditorController p_MapEditorController) {
        this.d_MapEditorController = p_MapEditorController;
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
     * @param p_options The options for showing the map.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_options) {
        System.out.println("Showing Map");
        return d_MapEditorController.showMap();
    }
}
