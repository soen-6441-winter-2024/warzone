package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapEditorCommandDispatcher {

    private final MapEditorShellComponent performer;

    public MapEditorCommandDispatcher(MapEditorShellComponent performer) {
        this.performer = performer;
    }

    public String exec(String[] commandParts) {



        return switch (commandParts[0].trim()) {
            case "editcontinent" -> doEditContinent(commandParts);
            default -> "";
        };
    }

    private String doEditContinent(String[] commandParts) {

        List<ContinentDto> addList = new ArrayList<>();
        List<String> removeList = new ArrayList<>();

        if (commandParts.length > 1) {

            for (int i = 1; i < commandParts.length; i++) {
                String[] optionParts = commandParts[i].split(" ");

                if ("add".equals(optionParts[0])) {
                    if (optionParts.length == 3) {
                        ContinentDto dto = new ContinentDto();
                        dto.setId(optionParts[1]);
                        dto.setValue(optionParts[2]);
                        addList.add(dto);
                    } else {
                        return "The option -add requires two values";
                    }

                } else if ("remove".equals(optionParts[0])) {
                    if (optionParts.length == 2) {
                        removeList.add(optionParts[1]);
                    }
                }
            }

            return performer.editContinents(addList, removeList);

        } else {
            return "This command requires at least one option";
        }
    }
}
