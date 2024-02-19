package ca.concordia.app.warzone.console.commands.implementations.subcommands;
import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

/**
 * Represents a sub-command for adding a neighbor to a country in a map editor.
 */
public class AddNeighborSubCommand extends SubCommand {

    private final MapEditorController d_mapEditorController;

    /**
     * Constructs an AddNeighborSubCommand with the specified options and map editor controller.
     *
     * @param options               The options for adding a neighbor. Expected length is 2.
     * @param p_mapEditorController The map editor controller to use for adding the neighbor.
     * @throws InvalidCommandException if the options length is not 2.
     */
    public AddNeighborSubCommand(String[] options, MapEditorController p_mapEditorController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = options;
        this.d_mapEditorController = p_mapEditorController;
    }

    /**
     * Runs the AddNeighborSubCommand by adding a neighbor to a country using the map editor controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call AddCountry from the service class
        CountryDto countryDto = new CountryDto();
        countryDto.setId(this.d_Options[0]);
        countryDto.setNeighborId(this.d_Options[1]);

        return d_mapEditorController.addNeighbor(countryDto);
    }
}

