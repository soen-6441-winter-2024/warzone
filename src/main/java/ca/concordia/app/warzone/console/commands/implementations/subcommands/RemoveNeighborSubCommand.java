package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class RemoveNeighborSubCommand extends SubCommand {

    private MapEditorController controller;
    public RemoveNeighborSubCommand(String[] options, MapEditorController controller) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.options = options;
        this.controller = controller;
    }

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        CountryDto countryDto = new CountryDto();
        countryDto.setId(this.options[0]);
        countryDto.setNeighborId(this.options[1]);
        System.out.println("Removing a country neighbor with Id Country: " + this.options[0] + " and Id Country Neighbor: " + this.options[1]) ;
        String response = controller.deleteNeighbor(countryDto);
        System.out.println(response);
        return response;
    }
}
