package ca.concordia.app.warzone.console.commands.implementations;
import ca.concordia.app.warzone.model.MapFileFormat;
import org.springframework.stereotype.Component;
import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

/**
 * Represents a command to load a map from a file.
 */
@Component
public class LoadMapCommand extends Command {
    /** The controller for map editing. */
    private final MapEditorController d_MapEditorController;

    /**
     * Constructs a LoadMapCommand object.
     *
     * @param p_mapEditorController The controller for map editing.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public LoadMapCommand(MapEditorController p_mapEditorController) throws InvalidCommandException {
        this.d_MapEditorController = p_mapEditorController;
        init();
        }

    private void init() {
        this.d_Type = CommandType.LOAD_MAP;
    }

    /**
     * Runs the load map command.
     *
     * @param p_options The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_options) {
        if(p_options.length < 1) {
            throw new InvalidCommandException("Expected at least filename parameter");
        }

        MapDto mapDto = new MapDto();
        mapDto.setFileName(p_options[0]);

        if (p_options.length > 1) {

            MapFileFormat format = getFormat(p_options[1]);
            mapDto.setFormat(format);
        }

        System.out.println("Loading Map file: " + p_options[0]);

        return d_MapEditorController.loadMap(mapDto);
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
