package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.Continent;
import org.springframework.stereotype.Service;
import ca.concordia.app.warzone.logging.LoggingService;
import ca.concordia.app.warzone.model.Strategies.HumanPlayerStrategy;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A service class that manages players in a Warzone game.
 * This class provides methods for adding and removing players, assigning
 * reinforcements, and adding orders.
 */
@Service
public class PlayerService {

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
     * Constructs a PlayerService with the specified PlayerRepository.
     *
     * @param p_repository       the PlayerRepository to be used
     * @param p_mapService       the MapService to be used
     * @param p_countryService   the CountryService to be used
     * @param p_continentService the ContinentService to be used
     * @param p_humanPlayerStrategy the HumanPlayerStrategy to be used
     */
    public PlayerService(PlayerRepository p_repository, MapService p_mapService, CountryService p_countryService,
            ContinentService p_continentService, HumanPlayerStrategy p_humanPlayerStrategy) {
        this.d_repository = p_repository;
        this.d_mapService = p_mapService;
        this.d_countryService = p_countryService;
        this.d_continentService = p_continentService;
        this.d_humanPlayerStrategy =  p_humanPlayerStrategy;
    }

    /**
     * Adds a new player.
     *
     * @param p_playerDto the DTO containing player information
     * @return a message indicating success or failure
     */
    public String add(PlayerDto p_playerDto) {
        String playerName = p_playerDto.getPlayerName();
        Optional<Player> playerOptional = findByName(playerName);
        if (playerOptional.isPresent()) {
            return "Player with name " + playerName + " already exists";
        } else {
            Player player = new Player();
            player.setPlayerName(playerName);
            d_repository.save(player);
            return "Player " + playerName + " joined the game";
        }
    }

    /**
     * Updates player information.
     *
     * @param p_player the player object to be updated
     */
    public void updatePlayer(Player p_player) {
        LoggingService.log("Updating Player: Id" + p_player.getId() + " name: " + p_player.getPlayerName());
        this.d_repository.save(p_player);
    }

    /**
     * Retrieves the cards assigned to a player.
     *
     * @param p_playerId the player to return their cards.
     */
    public String showPlayerCards(int p_playerId) {
        Player player = this.getAllPlayers().get(p_playerId);
        List<String> cards = player.d_cardsReceived;

        if(cards.size() == 0) return player.getPlayerName() + " You have special 0 cards.";

        return player.getPlayerName() + " your cards: " + cards.toString();
    }
    
    /**
     * Removes a player.
     *
     * @param p_playerName the name of the player to be removed
     * @return a message indicating success or failure
     */
    public String remove(String p_playerName) {
        Optional<Player> playerOptional = findByName(p_playerName);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            d_repository.deleteById(player.getId());
            return "Player " + p_playerName + " has been removed";
        } else {
            return "Player " + p_playerName + " not found";
        }
    }

    /**
     * Finds a player by name.
     *
     * @param p_playerName the name of the player to find
     * @return an Optional containing the player if found, empty otherwise
     */
    public Optional<Player> findByName(String p_playerName) {
        LoggingService.log("Finding Player: name: " + p_playerName);
        return d_repository.findById(p_playerName);
    }

    /**
     * Assigns reinforcements to players.
     *
     * @throws NotFoundException when players aren't found
     */
    public void assignReinforcements() throws NotFoundException {
        List<Player> playerList = this.d_repository.findAll();
        for (Player player : playerList) {
            int reinforcementsForPlayer = this.getReinforcementsForPlayer(player.getPlayerName());
            player.setNumberOfReinforcements(reinforcementsForPlayer);
            LoggingService.log("Assigning " + String.valueOf(reinforcementsForPlayer) + " to the player: "
                    + player.getPlayerName());
        }
    }

    /**
     * Retrieves the number of reinforcements for a player.
     *
     * @param p_playerName the name of the player
     * @return the number of reinforcements
     * @throws NotFoundException when players aren't found
     */
    private int getReinforcementsForPlayer(String p_playerName) throws NotFoundException {
        Optional<Player> playerOpt = this.findByName(p_playerName);
        if (playerOpt.isEmpty()) {
            LoggingService.log("player not found");
            throw new NotFoundException("player not found");
        }

        Player player = playerOpt.get();
        List<Continent> continents = this.d_continentService.findAll();

        int bonus = 0;

        for (Continent continent : continents) {
            List<Country> allCountriesForContinent = this.d_countryService.findByContinentId(continent.getId());
            int ownedByPlayer = 0;

            for (Country country : allCountriesForContinent)
                if (player.ownsCountry(country.getId()))
                    ownedByPlayer++;

            if (ownedByPlayer == allCountriesForContinent.size())
                bonus += Integer.parseInt(continent.getValue());
        }

        return DEFAULT_REINFORCEMENT_NUMBER + bonus;
    }

    /**
     * Add advance order to the current player
     *
     * @param p_countryFrom origin country
     * @param p_countryTo destination country
     * @param armiesQuantity quantity of armies
     * @param p_playerGivingOrder player id
     * @param gameTurn game turn
     * @param p_diplomacyList list of diplomacy players
     * @return result of the operation
     */
    public String addAdvanceOrderToCurrentPlayer(String p_countryFrom, String p_countryTo, int armiesQuantity,
            int p_playerGivingOrder, int gameTurn, List<List<String>> p_diplomacyList) {
        return d_humanPlayerStrategy.addAdvanceOrder(p_countryFrom, p_countryTo, armiesQuantity, p_playerGivingOrder,
                gameTurn, p_diplomacyList);
    }

    /**
     * Add airlift order to the current player
     *
     * @param p_countryFrom origin country
     * @param p_countryTo destination country
     * @param armiesQuantity quantity of armies
     * @param p_playerGivingOrder player id
     * @param gameTurn game turn
     * @return result of the operation
     */
    public String addAirliftOrderToCurrentPlayer(String p_countryFrom, String p_countryTo, int armiesQuantity, int p_playerGivingOrder, int gameTurn) {
        return d_humanPlayerStrategy.addAirliftOrder(p_countryFrom, p_countryTo, armiesQuantity, p_playerGivingOrder,
                gameTurn);
    }

    /**
     * Adds blockade order to the current player
     *
     * @param p_country country to block
     * @param p_playerGivingOrder player id
     * @param gameTurn game turn
     * @return result of the operation
     */
    public String addBlockadeOrderToCurrentPlayer(String p_country, int p_playerGivingOrder, int gameTurn) {
        return d_humanPlayerStrategy.addBlockadeOrder(p_country, p_playerGivingOrder, gameTurn);
    }

     /**
     * Validates and adds a deploy order to the current player's list of orders
     * 
     * @param p_countryId              the country to deploy armies to
     * @param p_numberOfReinforcements the number of army units to deploy
     * @param p_playerGivingOrder      the player currently giving orders
     * @param gameTurn                 the current round of the game
     * @return the state of the order
     */
    public String addDeployOrderToCurrentPlayer(String p_countryId, int p_numberOfReinforcements,
            int p_playerGivingOrder, int gameTurn) {
        return d_humanPlayerStrategy.addDeployOrder(p_countryId, p_numberOfReinforcements, p_playerGivingOrder, gameTurn);
    }

    /**
     * Validates and adds a bomb order to the current player's list of orders
     * 
     * @param p_targetCountryId           The country to bomb
     * @param p_playerGivingOrder the player currently giving orders
     * @param gameTurn            the current round of the game
     * @return the state of the bomb order
     */
    public String addBombOrderToCurrentPlayer(String p_targetCountryId, int p_playerGivingOrder, int gameTurn) {
        return d_humanPlayerStrategy.addBombOrder(p_targetCountryId, p_playerGivingOrder, gameTurn);
    }

    /**
     * Asks the current player to give a deploy order.
     *
     * @param p_playerGivingOrder player to ask
     */
    public void askForDeployOrder(int p_playerGivingOrder) {
        List<Player> players = this.getAllPlayers();
        Player player = players.get(p_playerGivingOrder);
        LoggingService.log("Player " + player.getPlayerName() + " give a deploy order. Reinforcements available: "
                + player.getNumberOfReinforcements());
        System.out.println("Player " + player.getPlayerName() + " give a deploy order. Reinforcements available: "
                + player.getNumberOfReinforcements());
    }

    /**
     * Asks the current player to give regular orders
     *
     * @param p_playerGivingOrder player to ask
     */
    public void askForRegularOrders(int p_playerGivingOrder) {
        List<Player> players = this.getAllPlayers();
        Player player = players.get(p_playerGivingOrder);
        LoggingService.log("Player " + player.getPlayerName() + " give an advance or a special order.");
        System.out.println("Player " + player.getPlayerName() + " give an advance or a special order.");
    }

    /**
     * Assigns countries to players at the start of the game.
     */
    public void assignCountries() {
        List<Player> players = this.getAllPlayers();
        List<Country> countries = this.d_countryService.findAll();

        Collections.shuffle(countries);

        int totalPlayers = players.size();
        int minCountriesPerPlayer = countries.size() / totalPlayers;
        int remainingCountries = countries.size() % totalPlayers;
        int i = 0;

        // Distribute the countries evenly among players
        for (Player player : players) {
            for (int j = 0; j < minCountriesPerPlayer; j++) {
                player.addCountry(countries.get(i));
                this.updatePlayer(player);
                countries.get(i).setPlayer(this.findByName(player.getPlayerName()));
                LoggingService.log(player.getPlayerName() + " was assigned " + countries.get(i));
                System.out.println(player.getPlayerName() + " was assigned " + countries.get(i));
                i++;
            }
        }

        // Distribute remaining countries
        for (int j = 0; j < remainingCountries; j++) {
            Player player = players.get(j);
            player.addCountry(countries.get(i));
            this.updatePlayer(player);
            countries.get(i).setPlayer(this.findByName(player.getPlayerName()));
            LoggingService.log(player.getPlayerName() + " was assigned " + countries.get(i));
            System.out.println(player.getPlayerName() + " was assigned " + countries.get(i));
            i++;
        }
    }

    /**
     * Retrieves all players.
     *
     * @return a list of all players
     */
    public List<Player> getAllPlayers() {
        return this.d_repository.findAll();
    }
}