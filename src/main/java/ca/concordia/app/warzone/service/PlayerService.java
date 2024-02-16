package ca.concordia.app.warzone.service;


import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.repository.PlayerRepository;

import ca.concordia.app.warzone.service.model.Continent;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class PlayerService {

    private final PlayerRepository repository;

    public  PlayerService(PlayerRepository repository){
        this.repository = repository;
    }

    public String add(PlayerDto playerDto){
        Player domain = new Player();
        domain.set_playerName(playerDto.get_playerName());
        repository.save(domain);
        return "player has joined the game";
    }

    public Optional<Player> findByName(String d_playerName) {
        return repository.findByName(d_playerName);
    }
}
