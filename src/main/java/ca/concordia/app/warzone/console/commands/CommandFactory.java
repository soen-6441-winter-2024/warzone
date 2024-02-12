package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.implementations.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;

public class CommandFactory {
    public static Command NewCommand(String commandName, String[] optionsOrSubCommands) throws InvalidCommandException {
        if(commandName.equals(CommandType.EDIT_CONTINENT.toString())) {
            return new EditContinentCommand(optionsOrSubCommands);
        } else if(commandName.equals(CommandType.EDIT_COUNTRY.toString())) {
            return new EditCountryCommand(optionsOrSubCommands);
        } else if(commandName.equals(CommandType.SHOW_MAP.toString())) {
            return new ShowMapCommand();
        } else if(commandName.equals(CommandType.EDIT_MAP.toString())) {
            return new EditMapCommand(optionsOrSubCommands);
        } else if(commandName.equals(CommandType.EDIT_NEIGHBOR.toString())){
            return new EditNeighborCommand(optionsOrSubCommands);
        } else if(commandName.equals(CommandType.SAVE_MAP.toString())) {
            return new SaveMapCommand(optionsOrSubCommands);
        }


        return null;
    }
}
