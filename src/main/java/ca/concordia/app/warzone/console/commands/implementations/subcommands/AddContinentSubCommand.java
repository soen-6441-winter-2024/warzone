package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class AddContinentSubCommand extends SubCommand {

    private final MapEditorController mapEditorController;

    public AddContinentSubCommand(String[] options, MapEditorController mapEditorController) throws InvalidCommandException {
        this.type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.options = options;
        this.mapEditorController = mapEditorController;
    }


    @Override
    public String run() {
        // Call AddContinent from the service class
        ContinentDto dto = new ContinentDto();
        dto.setId(this.options[0]);
        dto.setValue(this.options[1]);
        System.out.println("Adding a continent with values: " + dto.getId() + " " + dto.getValue());
        String response = mapEditorController.addContinent(dto);
        System.out.println(response);
        return response;
    }
}
