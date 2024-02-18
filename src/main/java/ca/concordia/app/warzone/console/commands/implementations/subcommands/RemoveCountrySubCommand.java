package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class RemoveCountrySubCommand extends SubCommand {

    private MapEditorController controller;
    public RemoveCountrySubCommand(String[] options, MapEditorController controller) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
        this.controller = controller;
    }

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        CountryDto dto = new CountryDto();
        dto.setId(this.options[0]);
        System.out.println("Removing a country with Id: " + dto.getId());
        String response = controller.deleteCountry(dto.getId());
        System.out.println(response);
        return response;
    }
}
