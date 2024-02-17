package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.implementations.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    private EditContinentCommand editContinentCommand;
    private EditCountryCommand editCountryCommand;
    private EditNeighborCommand editNeighborCommand;
    private ShowMapCommand showMapCommand;

    public CommandFactory(EditContinentCommand editContinentCommand, EditCountryCommand editCountryCommand, EditNeighborCommand editNeighborCommand, ShowMapCommand showMapCommand) {
        this.editContinentCommand = editContinentCommand;
        this.editCountryCommand = editCountryCommand;
        this.editNeighborCommand = editNeighborCommand;
        this.showMapCommand = showMapCommand;
    }

    public Command newCommand(String commandName) throws InvalidCommandException {
        if(commandName.equals(CommandType.EDIT_CONTINENT.toString())) {
            return editContinentCommand;
        } else if(commandName.equals(CommandType.EDIT_COUNTRY.toString())) {
            return editCountryCommand;
        } else if(commandName.equals(CommandType.EDIT_NEIGHBOR.toString())){
            return editNeighborCommand;
        } else if(commandName.equals(CommandType.SHOW_MAP.toString())) {
            return showMapCommand;
        }

        return null;
    }
}
