package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.model.DeployOrder;
import ca.concordia.app.warzone.service.model.Order;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing player-related operations.
 */
@Service
public class PlayerService {

    /**
     * Data member for storing orders.
     */
    private List<List<Order>> d_orders;

    /**
     * Data member for storing the current round number.
     */
    private int d_currentRound;

    /**
     * Default reinforcement number.
     */
    private final int DEFAULT_REINFORCEMENT_NUMBER = 3;

    /**
     * Repository for player operations.
     */
    private final PlayerRepository d_repository;

    /**
     * Constructs a PlayerService with the specified PlayerRepository.
     *
     * @param p_repository the PlayerRepository to be used
     */
    public PlayerService(PlayerRepository p_repository) {
        this.d_repository = p_repository;
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
     * Removes a player.
     *
     * @param p_playerName the name of the player to be removed
     * @return a message indicating success or failure
     */
    public String remove(String p_playerName) {
        Optional<Player> playerOptional = findByName(p_playerName);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            d_repository.delete(player);
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
        return d_repository.findByName(p_playerName);
    }

    /**
     * Assigns reinforcements to players.
     */
    public void assignReinforcements() {
        List<Player> playerList = this.d_repository.getAllPlayers();
        for (Player player : playerList) {
            int reinforcementsForPlayer = this.getReinforcementsForPlayer(player.getPlayerName());
            player.setNumberOfReinforcements(reinforcementsForPlayer);
        }
    }

    /**
     * Retrieves the number of reinforcements for a player.
     *
     * @param p_playerName the name of the player
     * @return the number of reinforcements
     */
    private int getReinforcementsForPlayer(String p_playerName) {
        return DEFAULT_REINFORCEMENT_NUMBER;
    }

    /**
     * Executes orders for the current round.
     */
    private void executeOrders() {
        List<Order> roundOrders = d_orders.get(d_currentRound);
        for (Order order : roundOrders) {
            this.executeOrder(order);
        }
    }

    /**
     * Executes a specific order.
     *
     * @param p_order the order to execute
     */
    private void executeOrder(Order p_order) {
        if (p_order instanceof DeployOrder) {
            // Add the reinforcements to the country
        }
    }
}