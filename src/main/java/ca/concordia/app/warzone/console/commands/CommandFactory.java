package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.implementations.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    private final AssignCountriesCommand assignCountriesCommand;
    private EditContinentCommand editContinentCommand;
    private EditCountryCommand editCountryCommand;
    private EditNeighborCommand editNeighborCommand;
    private ShowMapCommand showMapCommand;
    private GamePlayerCommand editGamePlayerCommand;
    private SaveMapCommand saveMapCommand;
    private EditMapCommand editMapCommand;

    public CommandFactory(EditContinentCommand editContinentCommand, EditCountryCommand editCountryCommand, EditNeighborCommand editNeighborCommand, ShowMapCommand showMapCommand, GamePlayerCommand editGamePlayerCommand, SaveMapCommand saveMapCommand, AssignCountriesCommand assignCountriesCommand, EditMapCommand editMapCommand) {
        this.editContinentCommand = editContinentCommand;
        this.editCountryCommand = editCountryCommand;
        this.editNeighborCommand = editNeighborCommand;
        this.showMapCommand = showMapCommand;
        this.editGamePlayerCommand = editGamePlayerCommand;
        this.saveMapCommand = saveMapCommand;
        this.editMapCommand = editMapCommand;
        this.assignCountriesCommand = assignCountriesCommand;
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
        } else if(commandName.equals(CommandType.GAME_PLAYER.toString())) {
            return editGamePlayerCommand;
        } else if(commandName.equals(CommandType.SAVE_MAP.toString())) {
            return saveMapCommand;
        } else if (commandName.equals(CommandType.EDIT_MAP.toString())) {
            return editMapCommand;
        } else if(commandName.equals(CommandType.ASSIGN_COUNTRIES.toString())){
            return  assignCountriesCommand;
        }

        return null;
    }
}
