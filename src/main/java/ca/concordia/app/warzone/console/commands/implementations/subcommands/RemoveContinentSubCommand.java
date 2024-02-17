package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

public class RemoveContinentSubCommand extends SubCommand {

    private MapEditorController mapEditorController;

    public RemoveContinentSubCommand(String[] options, MapEditorController mapEditorController) throws InvalidCommandException {
        this.type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.options = options;
        this.mapEditorController = mapEditorController;
    }

    @Override
    public String run() {
        // Call RemoveContinent from the service class
        ContinentDto dto = new ContinentDto();
        dto.setId(this.options[0]);
        System.out.println("Removing a continent with value: " + dto.getId());
        String response = mapEditorController.deleteContinent(dto.getId());
        System.out.println(response);
        return response;
    }
}
