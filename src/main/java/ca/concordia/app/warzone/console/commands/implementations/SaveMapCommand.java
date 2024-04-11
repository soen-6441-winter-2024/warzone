package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;
import ca.concordia.app.warzone.model.MapFileFormat;
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
        if (p_options.length < 1) {
            throw new InvalidCommandException("Expected at least filename parameter");
        }
        MapDto mapDto = new MapDto();
        mapDto.setFileName(p_options[0]);

        if (p_options.length > 1) {

            MapFileFormat format = getFormat(p_options[1]);
            mapDto.setFormat(format);
        }

        System.out.println("Saving a Map with FileName: " + p_options[0]);
        return d_MapEditorController.saveMap(mapDto);
    }

    private MapFileFormat getFormat(String format) {

        try {
            return MapFileFormat.valueOf(format);
        } catch (Exception e) {
            System.out.println("Invalid format, defaulting");
            return MapFileFormat.DEFAULT;
        }
    }
}
