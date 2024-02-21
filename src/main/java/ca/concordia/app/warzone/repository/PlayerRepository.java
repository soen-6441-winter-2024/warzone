package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.model.Player;

import java.util.List;
import java.util.Optional;

/**
 * Interface for accessing and managing players.
 */
public interface PlayerRepository {

    /**
     * Saves a player.
     *
     * @param p_playerDto the player to save
     */
    void save(Player p_playerDto);

    /**
     * Deletes a player.
     *
     * @param p_playerDto the player to delete
     */
    void delete(Player p_playerDto);

    /**
     * Finds a player by their name.
     *
     * @param p_playerName the name of the player to find
     * @return an Optional containing the player, or empty if not found
     */
    Optional<Player> findByName(String p_playerName);

    /**
     * Retrieves all players.
     *
     * @return a list of all players
     */
    List<Player> getAllPlayers();
}
