package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.springframework.stereotype.Component;

/**
 * Represents a command to save a map.
 */
@Component
public class SaveMapCommand extends Command {

    /** The controller for map editing. */
    private final MapEditorController d_MapEditorController;

    /**
     * Constructs a SaveMapCommand object.
     *
     * @param p_mapEditorController The controller for map editing.
     */
    public SaveMapCommand(MapEditorController p_mapEditorController) {
        this.d_MapEditorController = p_mapEditorController;
        init();
    }

    /**
     * Initializes the command type.
     */
    private void init() {
        this.d_Type = CommandType.SAVE_MAP;
    }

    /**
     * Runs the save map command.
     *
     * @param p_options The options for saving the map.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_options) {
        if (p_options.length != 1) {
            throw new InvalidCommandException("filename expected");
        }
        MapDto mapDto = new MapDto();
        mapDto.setFileName(p_options[0]);
        System.out.println("Saving a Map with FileName: " + p_options[0]);
        return d_MapEditorController.saveMap(mapDto);
    }
}
