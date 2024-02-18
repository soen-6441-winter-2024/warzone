package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PlayerRepositoryMemoryImpl implements PlayerRepository {
    private Map<String, Player> players = new HashMap<>();

    @Override
    public void save(Player domain) {
        players.put(domain.get_playerName(), domain);
    }

    public void delete(Player player){
        players.remove(player.get_playerName(), player);
    }

    @Override
    public Optional<Player> findByName(String name) {
        return Optional.ofNullable(players.get(name));
    }

    @Override
    public List<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }
}
