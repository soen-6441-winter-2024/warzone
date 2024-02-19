package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;


/**
 * Represents a sub-command for adding a continent in a map editor.
 */
public class AddContinentSubCommand extends SubCommand {

    private final MapEditorController d_mapEditorController;

    /**
     * Constructs an AddContinentSubCommand with the specified options and map editor controller.
     *
     * @param p_options            The options for adding a continent. Expected length is 2.
     * @param p_mapEditorController The map editor controller to use for adding the continent.
     * @throws InvalidCommandException if the options length is not 2.
     */
    public AddContinentSubCommand(String[] p_options, MapEditorController p_mapEditorController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (p_options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = p_options;
        this.d_mapEditorController = p_mapEditorController;
    }

    /**
     * Runs the AddContinentSubCommand by adding a continent using the map editor controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call AddContinent from the service class
        ContinentDto dto = new ContinentDto();
        dto.setId(this.d_Options[0]);
        dto.setValue(this.d_Options[1]);
        return d_mapEditorController.addContinent(dto);
    }
}

