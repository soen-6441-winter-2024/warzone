package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.model.Player;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Implementation of the PlayerRepository interface using an in-memory data structure.
 */
@Repository
public class PlayerRepositoryMemoryImpl extends AbstractRepositoryMemoryImpl<Player> implements PlayerRepository {
    private Map<String, Player> d_players = new HashMap<>(); // Stores players with their names as keys

    @Override
    Map<String, Player> getMap() {
        return d_players;
    }
}
