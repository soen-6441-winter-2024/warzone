package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

public class AddCountrySubCommand extends SubCommand {

    private GameEngineController controller;

    public AddCountrySubCommand(String[] options, GameEngineController controller) throws InvalidCommandException {
        this.type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.options = options;
        this.controller = controller;
    }

    @Override
    public void run() {
        // Call AddCountry from the service class
        System.out.println("Adding a country with values: " + this.options[0] + " " + this.options[1]);
        CountryDto dto = new CountryDto(this.options[0], this.options[1]);
        controller.addCountry(dto);
    }
}
