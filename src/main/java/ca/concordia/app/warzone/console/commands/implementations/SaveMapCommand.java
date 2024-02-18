package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;



@Component
public class SaveMapCommand extends Command {

    private final MapEditorController mapEditorController;

    public SaveMapCommand(MapEditorController mapEditorController) {
        this.mapEditorController = mapEditorController;
        init();
    }

    private void init() {
        this.type = CommandType.SAVE_MAP;
    }

    @Override
    public String run(String[] options) {
        if (options.length != 1) {
            throw new InvalidCommandException("filename expected");
        }
        MapDto mapDto = new MapDto();
        mapDto.setFileName(options[0]);
        System.out.println("Saving a Map with FileName: " + options[0]);
        String response = mapEditorController.saveMap(mapDto);
        System.out.println(response);
        return response;
    }
}
