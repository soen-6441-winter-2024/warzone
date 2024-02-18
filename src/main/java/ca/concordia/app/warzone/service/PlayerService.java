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
        String d_playerName = playerDto.get_playerName();
        Optional<Player> playerOptional = findByName(d_playerName);
        if(playerOptional.isPresent()){
            return "player with name " + d_playerName + " already exists";
        }
        else{
        Player domain = new Player();
            domain.set_playerName(d_playerName);
        repository.save(domain);
            return "player " + d_playerName + " joined the game";
        }

    }

    public String remove(String d_playerName){
        Optional<Player> playerOptional = findByName(d_playerName);
        if(playerOptional.isPresent()){
            Player player = playerOptional.get();
            repository.delete(player);
            return "Player " + d_playerName + " has been removed";
    }
        else {
            return "Player " + d_playerName + " not found";
        }
    }



    public Optional<Player> findByName(String d_playerName) {
        return repository.findByName(d_playerName);
    }
}
