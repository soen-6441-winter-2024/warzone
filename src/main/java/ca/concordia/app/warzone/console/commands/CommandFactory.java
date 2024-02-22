package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.implementations.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import org.springframework.stereotype.Component;

/**
 * Factory class to create command objects based on the given command name.
 */
@Component
public class CommandFactory {

    /**
     * The command for assigning countries.
     */
    private final AssignCountriesCommand d_AssignCountriesCommand;
    private EditContinentCommand d_EditContinentCommand;
    private EditCountryCommand d_EditCountryCommand;
    private EditNeighborCommand d_EditNeighborCommand;
    private ShowMapCommand d_ShowMapCommand;
    private GamePlayerCommand d_EditGamePlayerCommand;
    private SaveMapCommand d_SaveMapCommand;
    private EditMapCommand d_EditMapCommand;
    private LoadMapCommand d_loadMapCommand;
    private DeployCommand d_deployCommand;
    private ValidateMapCommand d_validateMapCommand;
    private NextPhaseCommand d_nextPhaseCommand;

    /**
     * Constructs a CommandFactory object.
     *
     * @param p_editContinentCommand The command for editing continents.
     * @param p_editCountryCommand The command for editing countries.
     * @param p_editNeighborCommand The command for editing neighbors.
     * @param p_showMapCommand The command for showing the map.
     * @param p_editGamePlayerCommand The command for editing game players.
     * @param p_saveMapCommand The command for saving the map.
     * @param p_assignCountriesCommand The command for assigning countries.
     * @param p_editMapCommand         The command for editing the map.
     * @param p_loadMap                The command for loading the map.
     * @param p_deployCommand          The command to deploy
     * @param p_nextPhaseCommand       The command for going to the next phase
     * @param p_validateMapCommand     The command to validate a created or load map in memory
     */
    public CommandFactory(EditContinentCommand p_editContinentCommand, EditCountryCommand p_editCountryCommand,
                          EditNeighborCommand p_editNeighborCommand, ShowMapCommand p_showMapCommand,
                          GamePlayerCommand p_editGamePlayerCommand, SaveMapCommand p_saveMapCommand,
                          AssignCountriesCommand p_assignCountriesCommand, EditMapCommand p_editMapCommand,
                          LoadMapCommand p_loadMap, DeployCommand p_deployCommand, NextPhaseCommand p_nextPhaseCommand,
                          ValidateMapCommand p_validateMapCommand) {
        this.d_EditContinentCommand = p_editContinentCommand;
        this.d_EditCountryCommand = p_editCountryCommand;
        this.d_EditNeighborCommand = p_editNeighborCommand;
        this.d_ShowMapCommand = p_showMapCommand;
        this.d_EditGamePlayerCommand = p_editGamePlayerCommand;
        this.d_SaveMapCommand = p_saveMapCommand;
        this.d_EditMapCommand = p_editMapCommand;
        this.d_AssignCountriesCommand = p_assignCountriesCommand;
        this.d_loadMapCommand = p_loadMap;
        this.d_deployCommand = p_deployCommand;
        this.d_nextPhaseCommand = p_nextPhaseCommand;
        this.d_validateMapCommand = p_validateMapCommand;
    }

    /**
     * Creates a new command based on the given command name.
     *
     * @param p_commandName The name of the command.
     * @return A command object corresponding to the given command name.
     * @throws InvalidCommandException If the command name is invalid.
     */
    public Command newCommand(String p_commandName) throws InvalidCommandException {
        if (p_commandName.equals(CommandType.EDIT_CONTINENT.toString())) {
            return d_EditContinentCommand;
        } else if (p_commandName.equals(CommandType.EDIT_COUNTRY.toString())) {
            return d_EditCountryCommand;
        } else if (p_commandName.equals(CommandType.EDIT_NEIGHBOR.toString())) {
            return d_EditNeighborCommand;
        } else if (p_commandName.equals(CommandType.SHOW_MAP.toString())) {
            return d_ShowMapCommand;
        } else if (p_commandName.equals(CommandType.GAME_PLAYER.toString())) {
            return d_EditGamePlayerCommand;
        } else if (p_commandName.equals(CommandType.SAVE_MAP.toString())) {
            return d_SaveMapCommand;
        } else if (p_commandName.equals(CommandType.EDIT_MAP.toString())) {
            return d_EditMapCommand;
        } else if (p_commandName.equals(CommandType.ASSIGN_COUNTRIES.toString())) {
            return  d_AssignCountriesCommand;
        } else if (p_commandName.equals(CommandType.LOAD_MAP.toString())) {
            return d_loadMapCommand;
        } else if (p_commandName.equals(CommandType.DEPLOY.toString())) {
            return d_deployCommand;
        } else if (p_commandName.equals(CommandType.NEXT_PHASE.toString())) {
            return d_nextPhaseCommand;
        } else if (p_commandName.equals(CommandType.VALIDATE_MAP.toString())) {
            return d_validateMapCommand;
        }

        throw new InvalidCommandException("invalid command");
    }
}
