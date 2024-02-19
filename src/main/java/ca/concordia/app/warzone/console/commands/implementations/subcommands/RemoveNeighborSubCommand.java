package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

/**
 * Represents a sub-command for removing a neighbor from a country in a map editor.
 */
public class RemoveNeighborSubCommand extends SubCommand {

    private final MapEditorController d_controller;

    /**
     * Constructs a RemoveNeighborSubCommand with the specified options and map editor controller.
     *
     * @param options    The options for removing a neighbor. Expected length is 2.
     * @param p_controller The map editor controller to use for removing the neighbor.
     * @throws InvalidCommandException if the options length is not 2.
     */
    public RemoveNeighborSubCommand(String[] options, MapEditorController p_controller) throws InvalidCommandException {
        this.d_Type = SubCommandType.REMOVE;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = options;
        this.d_controller = p_controller;
    }

    /**
     * Runs the RemoveNeighborSubCommand by removing a neighbor from a country using the map editor controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call RemoveContinent from the service class
        CountryDto countryDto = new CountryDto();
        countryDto.setId(this.d_Options[0]);
        countryDto.setNeighborId(this.d_Options[1]);
        System.out.println("Removing a country neighbor with Id Country: " + this.d_Options[0] + " and Id Country Neighbor: " + this.d_Options[1]);
        return d_controller.deleteNeighbor(countryDto);
    }
}

