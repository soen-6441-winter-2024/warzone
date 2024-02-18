package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class RemoveNeighborSubCommand extends SubCommand {

    private MapEditorController controller;
    public RemoveNeighborSubCommand(String[] options, MapEditorController controller) throws InvalidCommandException {
        this.d_Type = SubCommandType.REMOVE;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = options;
        this.controller = controller;
    }

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        CountryDto countryDto = new CountryDto();
        countryDto.setId(this.d_Options[0]);
        countryDto.setNeighborId(this.d_Options[1]);
        System.out.println("Removing a country neighbor with Id Country: " + this.d_Options[0] + " and Id Country Neighbor: " + this.d_Options[1]) ;
        return controller.deleteNeighbor(countryDto);
    }
}
