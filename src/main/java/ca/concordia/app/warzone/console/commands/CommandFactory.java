package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.implementations.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    private EditContinentCommand editContinentCommand;

    private EditCountryCommand editCountryCommand;

    public CommandFactory(EditContinentCommand editContinentCommand, EditCountryCommand editCountryCommand) {
        this.editContinentCommand = editContinentCommand;
        this.editCountryCommand = editCountryCommand;
    }

    public Command newCommand(String commandName) throws InvalidCommandException {
        if(commandName.equals(CommandType.EDIT_CONTINENT.toString())) {
            return editContinentCommand;
        } else if(commandName.equals(CommandType.EDIT_COUNTRY.toString())) {
            return editCountryCommand;
        }
//        } else if(commandName.equals(CommandType.SHOW_MAP.toString())) {
//            return new ShowMapCommand();
//        } else if(commandName.equals(CommandType.EDIT_MAP.toString())) {
//            return new EditMapCommand(optionsOrSubCommands);
//        } else if(commandName.equals(CommandType.EDIT_NEIGHBOR.toString())){
//            return new EditNeighborCommand(optionsOrSubCommands);
//        } else if(commandName.equals(CommandType.SAVE_MAP.toString())) {
//            return new SaveMapCommand(optionsOrSubCommands);
//        }else if(commandName.equals(CommandType.LOAD_MAP.toString())) {
//            return new LoadMapCommand(optionsOrSubCommands);
//        }else if(commandName.equals(CommandType.ASSIGN_COUNTRIES.toString())) {
//            return new AssignCountriesCommand(optionsOrSubCommands);
//        }else if(commandName.equals(CommandType.GAME_PLAYER.toString())) {
//            return new GamePlayerCommand(optionsOrSubCommands);
//        }else if(commandName.equals(CommandType.DEPLOY.toString())) {
//            return new DeployCommand(optionsOrSubCommands);
//        }

        return null;
    }
}
