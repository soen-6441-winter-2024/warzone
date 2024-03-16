package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.orders.DeployOrder;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.*;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import ca.concordia.app.warzone.logging.LoggingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing game engine operations.
 */
@Component
public class GameEngineController {

    /**
     * Data member for storing orders.
     */
    // List<List<Order>> d_orders;

    // /**
    // * Data member for storing the current round number.
    // */
    private int d_currentRound;
    private int d_currentPlayerGivingOrder;

    /**
     * ContinentService for continent-related operations
     */
    private final ContinentService d_continentService;
    /**
     * Country service for country-related operations
     */
    private final CountryService d_countryService;
    /**
     * PlayerService for player-related operations
     */
    private final PlayerService d_playerService;
    /**
     * MapService for map-related operations
     */
    private final MapService d_mapService;
    /**
     * PhaseRepository for fetching and setting the current game phase
     */
    private final PhaseRepository d_phaseRepository;

    private final PlayerCardService d_playerCardService;

    /**
     * Constructs a GameEngineController with the specified services.
     *
     * @param p_continentService  The ContinentService to use.
     * @param p_countryService    The CountryService to use.
     * @param p_playerService     The PlayerService to use.
     * @param p_mapService        The MapService to use.
     * @param p_ordersService     The CountryService to be used
     * @param p_phaseRepository   The PhaseRepository to use.
     * @param p_PlayerCardService The PlayerCardService to use.
     */
    public GameEngineController(ContinentService p_continentService, CountryService p_countryService,
            PlayerService p_playerService, MapService p_mapService,
            PhaseRepository p_phaseRepository, PlayerCardService p_PlayerCardService) {
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
        this.d_playerService = p_playerService;
        this.d_mapService = p_mapService;
        this.d_phaseRepository = p_phaseRepository;
        this.d_playerCardService = p_PlayerCardService;
    }

    /**
     * Adds a continent to the game.
     *
     * @param p_continentDto The ContinentDto representing the continent to add.
     * @return A string indicating the result of the operation.
     */
    public String addContinent(ContinentDto p_continentDto) {
        String result = "";
        if (Phase.MAP_EDITOR.equals(this.d_phaseRepository.getPhase())) {
            result = d_continentService.add(p_continentDto);
        } else {
            result = "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Deletes a continent from the game.
     *
     * @param p_continentId The ID of the continent to delete.
     * @return A string indicating the result of the operation.
     */
    public String deleteContinent(String p_continentId) {
        // Implementation goes here
        return null;
    }

    /**
     * Removes a country from the game.
     *
     * @param p_option The option specifying the country to remove.
     * @return A string indicating the result of the operation.
     */
    public String removeCountry(String p_option) {
        // Implementation goes here
        return null;
    }

    /**
     * Adds a country to the game.
     *
     * @param p_dto The CountryDto representing the country to add.
     * @return A string indicating the result of the operation.
     */
    public String addCountry(CountryDto p_dto) {
        d_countryService.add(p_dto);
        return "Country " + p_dto.getId() + " added";
    }

    /**
     * Adds a player to the game.
     *
     * @param p_playerDto The PlayerDto representing the player to add.
     * @return A string indicating the result of the operation.
     */
    public String addPlayer(PlayerDto p_playerDto) {
        String playerName = p_playerDto.getPlayerName();
        LoggingService.log(playerName);
        return d_playerService.add(p_playerDto);
    }

    /**
     * Removes a player from the game.
     *
     * @param p_playerName The name of the player to remove.
     * @return A string indicating the result of the operation.
     */
    public String removePlayer(String p_playerName) {
        String result = d_playerService.remove(p_playerName);
        LoggingService.log(result);
        return result;
    }

    /**
     * Randomly assigns the countries to the players
     * 
     * @return the result of the operation
     * @throws NotFoundException when countries aren't found
     */
    public String assignCountries() throws NotFoundException {
        if (this.d_phaseRepository.getPhase() != Phase.STARTUP) {
            LoggingService.log("game not in startup phase");
            throw new InvalidCommandException("game not in startup phase");
        }

        this.d_playerService.assignCountries();
        this.d_phaseRepository.setPhase(Phase.GAME_LOOP);

        System.out.println("\nCountries Assigned. Let's Play!");

        this.startGameLoop();
        return "";
    }

    /**
     * Deploys a given number of armies into a specified country
     * 
     * @param countryId           the id of the country
     * @param numOfReinforcements the number of reinforcement armies to deploy
     * @return the result of the operation
     */
    public String deploy(String countryId, int numOfReinforcements) {
        if (this.d_phaseRepository.getPhase() != Phase.GAME_LOOP) {
            LoggingService.log("game is not in game loop phase");
            throw new InvalidCommandException("game is not in game loop phase");
        }

        return this.d_playerService.addDeployOrderToCurrentPlayer(countryId, numOfReinforcements,
                d_currentPlayerGivingOrder, d_currentRound);

    }

    /**
     * Issues an order to advance on a country.
     * @return state of issuing an advance order.
     */
    public String advance() {
        if (this.d_phaseRepository.getPhase() != Phase.GAME_LOOP) {
            LoggingService.log("game is not in game loop phase");
            throw new InvalidCommandException("game is not in game loop phase");
        }

        // call addAdvanceOrder method on player service
        return "";
    }

    /**
     * Notifies the game engine that a player has issued all their orders for the current round. 
     * if the notifying player is the last player of the game, then the orders are executed and the next round is started.
     * @return state of the current turn
     * @throws NotFoundException
     */
    public String turnOrdersComplete() throws NotFoundException {
        // all players have given their orders for the current round, now we execute all
        // orders
        if ((this.d_currentPlayerGivingOrder + 1) == this.d_playerService.getAllPlayers().size()) {
            System.out.println("\nExecuting orders for round " + (this.d_currentRound + 1));
            executeTurnOrders(this.d_currentRound);

            // next round
            this.d_currentRound++;
            // reset back to player 1 for the next turn of orders
            this.d_currentPlayerGivingOrder = 0;

            // assign reinforcement for next round
            this.d_playerService.assignReinforcements();

            System.out.println("\n------------------------------\n" 
            + "\tNext Round" + "\n------------------------------\n");
            
            this.d_playerService.askForDeployOrder(d_currentPlayerGivingOrder);
            return "";
        }
        // next player can issue their orders
        else {
            this.d_currentPlayerGivingOrder++;
            this.d_playerService.askForDeployOrder(d_currentPlayerGivingOrder);

            return "";
        }
    }

    /**
     * Method to go to the next phase, depending on current phase
     *
     * @return the phase after the change of phase
     */
    public String nextPhase() {

        Phase nextPhase = switch (d_phaseRepository.getPhase()) {
            case MAP_EDITOR -> Phase.STARTUP;
            case STARTUP -> Phase.GAME_PLAY;
            default -> Phase.GAME_LOOP;
        };

        this.d_phaseRepository.setPhase(nextPhase);
        LoggingService.log("Current phase is " + nextPhase);
        return "Current phase is " + nextPhase;
    }

    /**
     * Starts the game loop.
     *
     * @throws NotFoundException when players aren't found
     */
    public void startGameLoop() throws NotFoundException {
        // assigns reinforcement to players for the first round.
        this.d_playerService.assignReinforcements();

        this.d_currentRound = 0;
        this.d_currentPlayerGivingOrder = 0;

        LoggingService.log("Time to give deploy orders");
        d_playerService.askForDeployOrder(d_currentPlayerGivingOrder);
    }

    /**
     * Executes the order for the current turn following the command design pattern
     * 
     * @param p_currentRound the current round of the game
     */
    public void executeTurnOrders(int p_currentRound) {
        List<Player> players = this.d_playerService.getAllPlayers();

        for (Player player : players) {
            for (Order order : player.getPlayerCurrentTurnOrders(p_currentRound)) {
                order.execute();
            }
        }
    }
}
