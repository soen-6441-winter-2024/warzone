package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.model.Player;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Implementation of the PlayerRepository interface using an in-memory data structure.
 */
@Repository
public class PlayerRepositoryMemoryImpl implements PlayerRepository {
    private Map<String, Player> d_players = new HashMap<>(); // Stores players with their names as keys

    /**
     * Default constructor
     */
    public PlayerRepositoryMemoryImpl() {}
    /**
     * Saves a player to the repository.
     *
     * @param p_player the player to be saved
     */
    @Override
    public void save(Player p_player) {
        d_players.put(p_player.getPlayerName(), p_player);
    }

    /**
     * Deletes a player from the repository.
     *
     * @param p_player the player to be deleted
     */
    public void delete(Player p_player) {
        d_players.remove(p_player.getPlayerName(), p_player);
    }

    /**
     * Finds a player by their name.
     *
     * @param p_name the name of the player to find
     * @return an Optional containing the player, or empty if not found
     */
    @Override
    public Optional<Player> findByName(String p_name) {
        return Optional.ofNullable(d_players.get(p_name));
    }

    /**
     * Retrieves all players stored in the repository.
     *
     * @return a list of all players
     */
    @Override
    public List<Player> getAllPlayers() {
        return new ArrayList<>(d_players.values());
    }
}
