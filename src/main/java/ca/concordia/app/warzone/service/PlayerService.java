package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.orders.DeployOrder;
import ca.concordia.app.warzone.model.Continent;
import org.springframework.stereotype.Service;
import ca.concordia.app.warzone.logging.LoggingService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A service class that manages players in a Warzone game.
 * This class provides methods for adding and removing players, assigning reinforcements, and adding orders.
 */
@Service
public class PlayerService {

    /**
     * Data member for storing orders.
     */
    List<List<Order>> d_orders;

    /**
     * Data member for storing the current round number.
     */
    private int d_currentRound;

    private int currentPlayerGivingOrder;

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
     *  Service for map operations
     */
    private final MapService d_mapService;

    private final OrdersService d_ordersService;

    /**
     * Service for continent-related operations
     */
    private final ContinentService d_continentService;


    /**
     * Constructs a PlayerService with the specified PlayerRepository.
     *
     * @param p_repository     the PlayerRepository to be used
     * @param p_mapService     the MapService to be used
     * @param p_countryService the CountryService to be used
     * @param p_ordersService the CountryService to be used
     * @param p_continentService  the ContinentService to be used
     */
    public PlayerService(PlayerRepository p_repository, MapService p_mapService, CountryService p_countryService, OrdersService p_ordersService, ContinentService p_continentService) {
        this.d_repository = p_repository;
        this.d_mapService = p_mapService;
        this.d_countryService = p_countryService;
        this.d_ordersService = p_ordersService;
        this.d_continentService = p_continentService;
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
        LoggingService.log("Updating Player: Id" +  p_player.getId() + " name: " + p_player.getPlayerName());
        this.d_repository.save(p_player);
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
            LoggingService.log("Assigning " + String.valueOf(reinforcementsForPlayer) + " to the player: "+ player.getPlayerName());
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
        if(playerOpt.isEmpty()) {
            LoggingService.log("player not found");
            throw new NotFoundException("player not found");
        }

        Player player = playerOpt.get();
        List<Continent> continents = this.d_continentService.findAll();

        int bonus = 0;

        for(Continent continent : continents) {
            List<Country> allCountriesForContinent = this.d_countryService.findByContinentId(continent.getId());
            int ownedByPlayer = 0;

            for(Country country : allCountriesForContinent)
                if(player.ownsCountry(country.getId()))
                    ownedByPlayer++;

            if(ownedByPlayer == allCountriesForContinent.size())
                bonus += Integer.parseInt(continent.getValue());
        }



        return DEFAULT_REINFORCEMENT_NUMBER + bonus;
    }

    public void startGame() throws NotFoundException {
        this.d_currentRound = 0;
        this.currentPlayerGivingOrder = 0;

        this.d_orders = new ArrayList<>();
        this.d_orders.add(new ArrayList<>());

        this.assignReinforcements();
    }

    /**
     * Adds a deploy order for a player.
     *
     * @param d_countryId              the ID of the country to deploy armies to
     * @param d_numberOfReinforcements the number of armies to deploy
     * @throws InvalidCommandException if the player does not own the country or does not have enough reinforcements
     */
    public void addDeployOrder(String d_countryId, int d_numberOfReinforcements) throws InvalidCommandException {        
        Player player = this.getAllPlayers().get(this.currentPlayerGivingOrder);
        if (!player.ownsCountry(d_countryId)) {
            LoggingService.log("player does not own country");
            throw new InvalidCommandException("player does not own country");
        }

        if (d_numberOfReinforcements > player.getNumberOfReinforcements()) {
            LoggingService.log("player does not have enough reinforcements");
            throw new InvalidCommandException("player does not have enough reinforcements");
        }

        DeployOrder deployOrder = new DeployOrder(player.getPlayerName(), d_countryId, d_numberOfReinforcements);

        this.d_orders.get(this.d_currentRound).add(deployOrder);
        int numberOfReinforcements = player.getNumberOfReinforcements() - d_numberOfReinforcements;
        player.setNumberOfReinforcements(numberOfReinforcements);

        LoggingService.log("player: " + player.getPlayerName() + " set number of reinforcements: " + numberOfReinforcements);

        if (player.getNumberOfReinforcements() == 0) {
            this.currentPlayerGivingOrder++;
            
            if (this.currentPlayerGivingOrder == this.getAllPlayers().size()) {
                OrdersService ordersService = new OrdersService(d_countryService);
                ordersService.setOrders(this.d_orders);
                ordersService.executeOrders();
                LoggingService.log("Executing Orders");
                return;
            }
        }

        this.askForDeployOrder();
    }

    public void addDeployOrderToCurrentPlayer(String d_countryId, int d_numberOfReinforcements, int p_playerGivingOrder, int gameTurn) {
        Player player = this.getAllPlayers().get(p_playerGivingOrder);
        if (!player.ownsCountry(d_countryId)) {
            LoggingService.log("player does not own country");
            throw new InvalidCommandException("player does not own country");
        }

        if (d_numberOfReinforcements > player.getNumberOfReinforcements()) {
            LoggingService.log("player does not have enough reinforcements");
            throw new InvalidCommandException("player does not have enough reinforcements");
        }

        DeployOrder deployOrder = new DeployOrder(player.getPlayerName(), d_countryId, d_numberOfReinforcements);
        
        player.issueOrder(deployOrder, gameTurn);
    }

    /**
     * Asks the current player to give a deploy order.
     */
    public void askForDeployOrder() {
        List<Player> players = this.getAllPlayers();
        Player player = players.get(this.currentPlayerGivingOrder);
        LoggingService.log("Player " + player.getPlayerName() + " give your order. Reinforcements available: " + player.getNumberOfReinforcements());
        System.out.println("Player " + player.getPlayerName() + " give your order. Reinforcements available: " + player.getNumberOfReinforcements());
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