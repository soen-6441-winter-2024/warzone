package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class AddCountrySubCommand extends SubCommand {

    private MapEditorController mapEditorController;

    public AddCountrySubCommand(String[] options, MapEditorController mapEditorController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = options;
        this.mapEditorController = mapEditorController;
    }

    @Override
    public String run() {
        // Call AddCountry from the service class
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId(this.d_Options[1]);

        CountryDto countryDto = new CountryDto();
        countryDto.setId(this.d_Options[0]);
        countryDto.setContinent(continentDto);

        return mapEditorController.addCountry(countryDto);
    }
}
