package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

/**
 * Represents a sub-command for adding a country in a map editor.
 */
public class AddCountrySubCommand extends SubCommand {

    private MapEditorController d_mapEditorController;

    /**
     * Constructs an AddCountrySubCommand with the specified options and map editor controller.
     *
     * @param options            The options for adding a country. Expected length is 2.
     * @param p_mapEditorController The map editor controller to use for adding the country.
     * @throws InvalidCommandException if the options length is not 2.
     */
    public AddCountrySubCommand(String[] options, MapEditorController p_mapEditorController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = options;
        this.d_mapEditorController = p_mapEditorController;
    }

    /**
     * Runs the AddCountrySubCommand by adding a country using the map editor controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call AddCountry from the service class
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId(this.d_Options[1]);

        CountryDto countryDto = new CountryDto();
        countryDto.setId(this.d_Options[0]);
        countryDto.setContinent(continentDto);

        return d_mapEditorController.addCountry(countryDto);
    }
}

