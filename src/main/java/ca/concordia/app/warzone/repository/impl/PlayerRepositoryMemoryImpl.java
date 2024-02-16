package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PlayerRepositoryMemoryImpl implements PlayerRepository {
    private Map<String, Player> values = new HashMap<>();

    @Override
    public void save(Player domain) {
        values.put(domain.get_playerName(), domain);
    }

    @Override
    public Optional<Player> findByName(String name) {
        return Optional.ofNullable(values.get(name));
    }
}
