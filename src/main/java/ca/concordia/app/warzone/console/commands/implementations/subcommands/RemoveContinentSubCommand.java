package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

/**
 * Represents a sub-command for removing a continent from a map in a map editor.
 */
public class RemoveContinentSubCommand extends SubCommand {

    private final MapEditorController d_mapEditorController;

    /**
     * Constructs a RemoveContinentSubCommand with the specified options and map editor controller.
     *
     * @param options               The options for removing a continent. Expected length is 1.
     * @param d_mapEditorController The map editor controller to use for removing the continent.
     * @throws InvalidCommandException if the options length is not 1.
     */
    public RemoveContinentSubCommand(String[] options, MapEditorController d_mapEditorController) throws InvalidCommandException {
        this.d_Type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.d_Options = options;
        this.d_mapEditorController = d_mapEditorController;
    }

    /**
     * Runs the RemoveContinentSubCommand by removing a continent from a map using the map editor controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call RemoveContinent from the service class
        ContinentDto dto = new ContinentDto();
        dto.setId(this.d_Options[0]);
        return d_mapEditorController.deleteContinent(dto.getId());
    }
}

