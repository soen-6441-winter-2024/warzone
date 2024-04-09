package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.model.orders.*;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.PlayerService;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.Continent;
import org.springframework.stereotype.Service;
import ca.concordia.app.warzone.logging.LoggingService;
import ca.concordia.app.warzone.model.Strategies.HumanPlayerStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Abstract class representing the strategy for a human player in the game.
 */
@Service
public abstract class HumanStrategy {
    /**
     * Data member for storing orders.
     */
    List<List<Order>> d_orders;

    /**
     * Default reinforcement number.
     */
    private final int DEFAULT_REINFORCEMENT_NUMBER = 3;

    /**
     * Repository for player operations.
     */
    private final PlayerRepository d_repository;

    /**
     * Service for the country operations
     */
    private final CountryService d_countryService;

    /**
     * Service for map operations
     */
    private final MapService d_mapService;

    /**
     * Service for continent-related operations
     */
    private final ContinentService d_continentService;

    /**
     * Strategy for Human Player
     */
    private final HumanPlayerStrategy d_humanPlayerStrategy;

    /**
     * Service for Player
     */
    private final PlayerService d_playerService;

    /**
     * Retrieves the CountryService.
     *
     * @return the CountryService
     */
    protected CountryService getCountryService() {
        return d_countryService;
    }

    /**
     * Retrieves the PlayerRepository.
     *
     * @return the PlayerRepository
     */
    protected PlayerRepository getPlayerRepository() {
        return d_repository;
    }

    /**
     * Retrieves the PlayerService.
     *
     * @return the PlayerService
     */
    protected PlayerService getPlayerService() {
        return d_playerService;
    }

    /**
     * Constructs a HumanStrategy with the specified PlayerRepository.
     *
     * @param p_repository          the PlayerRepository to be used
     * @param p_mapService          the MapService to be used
     * @param p_countryService      the CountryService to be used
     * @param p_continentService    the ContinentService to be used
     * @param p_humanPlayerStrategy the HumanPlayerStrategy to be used
     * @param p_playerService       the PlayerService to be used
     */
    public HumanStrategy(PlayerRepository p_repository, MapService p_mapService, CountryService p_countryService, ContinentService p_continentService, HumanPlayerStrategy p_humanPlayerStrategy, PlayerService p_playerService) {
        this.d_repository = p_repository;
        this.d_mapService = p_mapService;
        this.d_countryService = p_countryService;
        this.d_continentService = p_continentService;
        this.d_humanPlayerStrategy =  p_humanPlayerStrategy;
        this.d_playerService =  p_playerService;
    }

    /**
     * Adds an advance order.
     *
     * @param p_countryFrom      the source country for the advance
     * @param p_countryTo        the target country for the advance
     * @param armiesQuantity     the number of armies to advance
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn           the current turn in the game
     * @param p_diplomacyList    the list of diplomacy contracts between players
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addAdvanceOrder(String p_countryFrom, String p_countryTo, int armiesQuantity,
                                           int p_playerGivingOrder, int gameTurn, List<List<String>> p_diplomacyList);

    /**
     * Adds an airlift order.
     *
     * @param p_countryFrom      the source country for the airlift
     * @param p_countryTo        the target country for the airlift
     * @param armiesQuantity     the number of armies to airlift
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn           the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addAirliftOrder(String p_countryFrom, String p_countryTo, int armiesQuantity,
                                           int p_playerGivingOrder, int gameTurn);

    /**
     * Adds a blockade order.
     *
     * @param p_country           the country to blockade
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addBlockadeOrder(String p_country, int p_playerGivingOrder, int gameTurn);

    /**
     * Adds a deploy order.
     *
     * @param p_countryId         the ID of the country to deploy armies to
     * @param p_numberOfReinforcements the number of armies to deploy
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addDeployOrder(String p_countryId, int p_numberOfReinforcements,
                                          int p_playerGivingOrder, int gameTurn);

    /**
     * Adds a bomb order.
     *
     * @param p_targetCountryId   the ID of the country to bomb
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return a message indicating the success or failure of the operation
     */
    public abstract String addBombOrder(String p_targetCountryId, int p_playerGivingOrder, int gameTurn);

    /**
     * Retrieves a list of all players.
     *
     * @return a list of all players
     */
    public abstract List<Player> getAllPlayers();

    /**
     * Prompts the specified player to give a deploy order.
     *
     * @param p_playerGivingOrder the index of the player to prompt
     */
    public abstract void askForDeployOrder(int p_playerGivingOrder);
}