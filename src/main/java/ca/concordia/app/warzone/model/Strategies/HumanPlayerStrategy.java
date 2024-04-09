package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.orders.*;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.PlayerService;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.logging.LoggingService;
import java.util.List;
import java.util.Optional;

/**
 * Represents the strategy for a human player in the game.
 */

public class HumanPlayerStrategy extends HumanStrategy {
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
    private final PlayerRepository d_playerRepository;

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
     * Service for Player
     */
    private final PlayerService d_playerService;

    /**
     * Constructs a HumanPlayerStrategy with the specified dependencies.
     *
     * @param p_playerRepository    the PlayerRepository to be used
     * @param p_mapService          the MapService to be used
     * @param p_countryService      the CountryService to be used
     * @param p_continentService    the ContinentService to be used
     * @param p_playerService       the PlayerService to be used
     */
    public HumanPlayerStrategy(PlayerRepository p_playerRepository, MapService p_mapService, CountryService p_countryService, ContinentService p_continentService, PlayerService p_playerService) {
        this.d_playerRepository = p_playerRepository;
        this.d_mapService = p_mapService;
        this.d_countryService = p_countryService;
        this.d_continentService = p_continentService;
        this.d_playerService = p_playerService;
    }

    /**
     * Adds an advance order to the player's list of orders.
     *
     * @param p_countryFrom      the source country for the advance
     * @param p_countryTo        the target country for the advance
     * @param armiesQuantity     the number of armies to advance
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn           the current turn in the game
     * @param p_diplomacyList    the list of diplomacy contracts between players
     * @return an empty string if successful, otherwise an error message
     */
    @Override
    public String addAdvanceOrder(String p_countryFrom, String p_countryTo, int armiesQuantity,
                                  int p_playerGivingOrder, int gameTurn, List<List<String>> p_diplomacyList) {
        Player player = this.getAllPlayers().get(p_playerGivingOrder);
        Optional<CountryDto> country = this.d_countryService.findById(p_countryTo);
        String ownerOfTargetCountry = country.get().getPlayer().getPlayerName();

        // check for diplomacy between player and target country's owner for the current
        // round
        if (p_diplomacyList.size() > 0) {
            for (List<String> diplomacyContract : p_diplomacyList) {
                if (diplomacyContract.get(0).equals(player.getPlayerName())
                        && diplomacyContract.get(1).equals(ownerOfTargetCountry)) {
                    return "Unable to issue attack order. Diplomacy is active between player "
                            + player.getPlayerName() + " and player " + ownerOfTargetCountry;
                }
            }
        }

        AdvanceOrder advanceOrder = new AdvanceOrder(player.getPlayerName(), p_countryFrom, p_countryTo, armiesQuantity,
                this.d_countryService);
        player.issueOrder(advanceOrder, gameTurn);

        return "";
    }

    /**
     * Adds an airlift order to the player's list of orders.
     *
     * @param p_countryFrom      the source country for the airlift
     * @param p_countryTo        the target country for the airlift
     * @param armiesQuantity     the number of armies to airlift
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn           the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    @Override
    public String addAirliftOrder(String p_countryFrom, String p_countryTo, int armiesQuantity, int p_playerGivingOrder, int gameTurn) {
        Player player = this.getAllPlayers().get(p_playerGivingOrder);

        AirliftOrder airliftOrder = new AirliftOrder(player.getPlayerName(), p_countryFrom, p_countryTo, armiesQuantity, this.d_countryService, this.d_playerService);
        player.issueOrder(airliftOrder, gameTurn);
        player.removeUsedCard("airlift_card");
        return "";
    }

    /**
     * Adds a blockade order to the player's list of orders.
     *
     * @param p_country           the country to blockade
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    @Override
    public String addBlockadeOrder(String p_country, int p_playerGivingOrder, int gameTurn) {
        Player player = this.getAllPlayers().get(p_playerGivingOrder);
        BlockadeOrder blockadeOrder = new BlockadeOrder(player.getPlayerName(), p_country, this.d_countryService);
        player.issueOrder(blockadeOrder, gameTurn);
        player.removeUsedCard("blockade_card");
        return  "";
    }

    /**
     * Adds a deploy order to the player's list of orders.
     *
     * @param p_countryId         the ID of the country to deploy armies to
     * @param p_numberOfReinforcements the number of armies to deploy
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    @Override
    public String addDeployOrder(String p_countryId, int p_numberOfReinforcements,
                                 int p_playerGivingOrder, int gameTurn) {
        Player player = this.getAllPlayers().get(p_playerGivingOrder);
        Optional<Country> countryToOptional = this.d_countryService.findCountryById(p_countryId);

        if(countryToOptional.isEmpty()){
            return "Country not found.";
        }

        Country countryTo = countryToOptional.get();

        if (!countryTo.getPlayer().get().getPlayerName().equals(player.getPlayerName())) {
            LoggingService.log("player does not own country");
            throw new InvalidCommandException("player does not own country");
        }

        if (p_numberOfReinforcements <= 0) {
            LoggingService.log("Player entered invalid reinforcement armies number");
            throw new InvalidCommandException("Invalid reinforcement armies number");
        }

        if (Math.abs(p_numberOfReinforcements) > player.getNumberOfReinforcements()) {
            LoggingService.log("player does not have enough reinforcements");
            throw new InvalidCommandException("player does not have enough reinforcements");
        }

        DeployOrder deployOrder = new DeployOrder(player.getPlayerName(), p_countryId, p_numberOfReinforcements,
                this.d_countryService);
        player.issueOrder(deployOrder, gameTurn);

        int numberOfReinforcements = player.getNumberOfReinforcements() - Math.abs(p_numberOfReinforcements);
        player.setNumberOfReinforcements(numberOfReinforcements);

        LoggingService
                .log("player: " + player.getPlayerName() + " set number of reinforcements: " + numberOfReinforcements);

        if (player.getNumberOfReinforcements() == 0) {
            return "";
        } else {
            // All of the current player's reinforcement have been deployed
            this.askForDeployOrder(p_playerGivingOrder);
        }

        return "";
    }

    /**
     * Adds a bomb order to the player's list of orders.
     *
     * @param p_targetCountryId   the ID of the country to bomb
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return a message indicating the success or failure of the operation
     */
    @Override
    public String addBombOrder(String p_targetCountryId, int p_playerGivingOrder, int gameTurn) {
        Player player = this.getAllPlayers().get(p_playerGivingOrder);
        Optional<CountryDto> country = this.d_countryService.findById(p_targetCountryId);

        if (!country.isPresent())
            throw new InvalidCommandException("Country not found.");

        // check if player has bomb card
        if (!player.hasCard("bomb_card")) {
            throw new InvalidCommandException(player.getPlayerName() + " You do not have a bomb card");
        }

        // create bomb order
        BombOrder bombOrder = new BombOrder(player.getPlayerName(), this.d_countryService, p_targetCountryId);
        player.issueOrder(bombOrder, gameTurn);
        player.removeUsedCard("bomb_card");

        LoggingService.log("player: " + player.getPlayerName() + " issued a bomb order on " + p_targetCountryId);

        return "Bomb order issued. Issue more advance or special orders.";
    }

    /**
     * Retrieves a list of all players.
     *
     * @return a list of all players
     */
    @Override
    public List<Player> getAllPlayers() {
        // Accessing the PlayerRepository using the method from the abstract class
        return this.d_playerRepository.findAll();
    }

    /**
     * Prompts the specified player to give a deploy order.
     *
     * @param p_playerGivingOrder the index of the player to prompt
     */
    @Override
    public void askForDeployOrder(int p_playerGivingOrder) {
        List<Player> players = this.getAllPlayers();
        Player player = players.get(p_playerGivingOrder);
        LoggingService.log("Player " + player.getPlayerName() + " give a deploy order. Reinforcements available: "
                + player.getNumberOfReinforcements());
        System.out.println("Player " + player.getPlayerName() + " give a deploy order. Reinforcements available: "
                + player.getNumberOfReinforcements());
    }
}