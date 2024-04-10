package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.implementations.*;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;

import org.springframework.cglib.core.ReflectUtils;
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
    private OrdersCompletedCommand d_OrdersCompletedCommand;
    private BombCommand d_BombCommand;
    private AdvanceCommand d_AdvanceCommand;
    private DiplomacyCommand d_DiplomacyCommand;
    private ShowPhaseCommand d_showPhaseCommand;
    private BlockadeCommand d_blockadeCommand;
    private SaveGameCommand d_saveGameCommand;

    private AirliftCommand d_AirliftCommand;
    private ShowMyCardsCommand d_ShowMyCardsCommand;

    private TournamentCommand d_TournamentCommand;

    /**
     * Constructs a CommandFactory object.
     *
     * @param p_editContinentCommand   The command for editing continents.
     * @param p_editCountryCommand     The command for editing countries.
     * @param p_editNeighborCommand    The command for editing neighbors.
     * @param p_showMapCommand         The command for showing the map.
     * @param p_editGamePlayerCommand  The command for editing game players.
     * @param p_saveMapCommand         The command for saving the map.
     * @param p_assignCountriesCommand The command for assigning countries.
     * @param p_editMapCommand         The command for editing the map.
     * @param p_loadMap                The command for loading the map.
     * @param p_deployCommand          The command to deploy
     * @param p_nextPhaseCommand       The command for going to the next phase
     * @param p_validateMapCommand     The command to validate a created or load map
     *                                 in memory
     * @param p_ordersCompletedCommand The command to notify game engine that player
     *                                 is done issuing commands for current round.
     * @param p_bombCommand            The command to issue a bomb order
     * @param p_advanceCommand         Command to attack or move army units across countries.
     * @param p_showPhaseCommand       Command to show which phase you are
     * @param p_blockadeCommand        Command to issue blockage order
     * @param p_diplomacyCommand       Command to issue diplomacy order
     *  @param p_airliftCommand         Command to issue airlift order
     * @param p_saveGameCommand         Command to save the game
     * @param p_ShowMyCardsCommand     Command to show current issuing player's special cards.
     * @param p_TournamentCommand      Command to start a game in Tournament Mode.
     */

    public CommandFactory(EditContinentCommand p_editContinentCommand, EditCountryCommand p_editCountryCommand,
            EditNeighborCommand p_editNeighborCommand, ShowMapCommand p_showMapCommand,
            GamePlayerCommand p_editGamePlayerCommand, SaveMapCommand p_saveMapCommand,
            AssignCountriesCommand p_assignCountriesCommand, EditMapCommand p_editMapCommand,
            LoadMapCommand p_loadMap, DeployCommand p_deployCommand, NextPhaseCommand p_nextPhaseCommand,
            ValidateMapCommand p_validateMapCommand, OrdersCompletedCommand p_ordersCompletedCommand,
            BombCommand p_bombCommand, AdvanceCommand p_advanceCommand, ShowPhaseCommand p_showPhaseCommand,
            BlockadeCommand p_blockadeCommand, DiplomacyCommand p_diplomacyCommand, AirliftCommand p_airliftCommand,
            ShowMyCardsCommand p_ShowMyCardsCommand, SaveGameCommand p_saveGameCommand, TournamentCommand p_TournamentCommand) {
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
        this.d_OrdersCompletedCommand = p_ordersCompletedCommand;
        this.d_BombCommand = p_bombCommand;
        this.d_AdvanceCommand = p_advanceCommand;
        this.d_showPhaseCommand = p_showPhaseCommand;
        this.d_DiplomacyCommand = p_diplomacyCommand;
        this.d_blockadeCommand = p_blockadeCommand;
        this.d_AirliftCommand = p_airliftCommand;
        this.d_ShowMyCardsCommand = p_ShowMyCardsCommand;
        this.d_saveGameCommand = p_saveGameCommand;
        this.d_TournamentCommand = p_TournamentCommand;
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
            return d_AssignCountriesCommand;
        } else if (p_commandName.equals(CommandType.LOAD_MAP.toString())) {
            return d_loadMapCommand;
        } else if (p_commandName.equals(CommandType.DEPLOY.toString())) {
            return d_deployCommand;
        } else if (p_commandName.equals(CommandType.NEXT_PHASE.toString())) {
            return d_nextPhaseCommand;
        } else if (p_commandName.equals(CommandType.VALIDATE_MAP.toString())) {
            return d_validateMapCommand;
        } else if (p_commandName.equals(CommandType.ORDERS_COMPLETED.toString())) {
            return d_OrdersCompletedCommand;
        } else if (p_commandName.equals(CommandType.BOMB_ORDER.toString())) {
            return d_BombCommand;
        } else if (p_commandName.equals(CommandType.ADVANCE.toString())) {
            return d_AdvanceCommand;
        } else if (p_commandName.equals(CommandType.SHOW_PHASE.toString())) {
            return d_showPhaseCommand;
        } else if (p_commandName.equals(CommandType.NEGOTIATE.toString())) {
            return d_DiplomacyCommand;
        } else if (p_commandName.equals(CommandType.BLOCKADE.toString())) {
            return d_blockadeCommand;
        } else if (p_commandName.equals(CommandType.AIRLIFT.toString())) {
            return d_AirliftCommand;
        } else if (p_commandName.equals(CommandType.SHOW_MY_CARDS.toString())) {
            return d_ShowMyCardsCommand;
        } else if (p_commandName.equals(CommandType.SAVEGAME.toString())) {
            return d_saveGameCommand;
        } else if (p_commandName.equals(CommandType.TOURNAMENT.toString())) {
            return d_TournamentCommand;
        }

        throw new InvalidCommandException("invalid command");
    }
}
