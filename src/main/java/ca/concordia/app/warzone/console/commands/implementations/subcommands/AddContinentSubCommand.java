package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class AddContinentSubCommand extends SubCommand {

    private final MapEditorController mapEditorController;

    public AddContinentSubCommand(String[] options, MapEditorController mapEditorController) throws InvalidCommandException {
        this.d_Type = SubCommandType.ADD;
        if (options.length != 2) {
            throw new InvalidCommandException("invalid options length, expected 2");
        }

        this.d_Options = options;
        this.mapEditorController = mapEditorController;
    }


    @Override
    public String run() {
        // Call AddContinent from the service class
        ContinentDto dto = new ContinentDto();
        dto.setId(this.d_Options[0]);
        dto.setValue(this.d_Options[1]);
        return mapEditorController.addContinent(dto);
    }
}
