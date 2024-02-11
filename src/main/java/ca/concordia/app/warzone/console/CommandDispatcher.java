package ca.concordia.app.warzone.console;

import org.springframework.stereotype.Component;

@Component
public class CommandDispatcher {

    MapEditorCommandDispatcher mapEditorCommandDispatcher;
    public String exec(String command) {
        if (!command.trim().isEmpty()) {
            String[] commandParts = command.split("-");
            return switch(commandParts[0]) {
                case "editcontinent",
                        "editcountry",
                        "editneighbor" -> mapEditorCommandDispatcher.exec(commandParts);
                default -> "";
            };
        } else {
            return "";
        }
    }
}
