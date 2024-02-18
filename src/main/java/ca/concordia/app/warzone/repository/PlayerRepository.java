package ca.concordia.app.warzone.repository;


import ca.concordia.app.warzone.service.model.Player;

import java.util.Optional;

public interface PlayerRepository {
    void save(Player playerDto);
    void delete(Player playerDto);


    Optional<Player> findByName(String Named_playerName);
}
