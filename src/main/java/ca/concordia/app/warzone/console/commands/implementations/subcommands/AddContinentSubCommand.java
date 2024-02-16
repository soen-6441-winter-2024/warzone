package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import ca.concordia.app.warzone.service.ContinentService;

public class AddContinentSubCommand extends SubCommand {

    private GameEngineController gameEngineController;

    public AddContinentSubCommand(String[] options, GameEngineController gameEngineController) throws InvalidCommandException {
        this.type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.options = options;
        this.gameEngineController = gameEngineController;
    }


    @Override
    public void run() {
        // Call AddContinent from the service class
        System.out.println("Adding a continent with values: " + this.options[0] + " " + this.options[1]);

        ContinentDto dto = new ContinentDto();
        dto.setId(this.options[0]);
        dto.setValue(this.options[1]);
        String response = gameEngineController.addContinent(dto);
        System.out.println(response);
    }
}
