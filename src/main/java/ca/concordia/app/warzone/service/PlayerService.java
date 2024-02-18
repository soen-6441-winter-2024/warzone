package ca.concordia.app.warzone.service;


import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.model.DeployOrder;
import ca.concordia.app.warzone.service.model.Order;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service

public class PlayerService {
    private List<List<Order>> orders;
    private int currentRound;

    private final int DEFAULT_REINFORCEMENT_NUMBER = 3;
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }


    public String add(PlayerDto playerDto) {
        String d_playerName = playerDto.get_playerName();
        Optional<Player> playerOptional = findByName(d_playerName);
        if (playerOptional.isPresent()) {
            return "player with name " + d_playerName + " already exists";
        } else {
        Player domain = new Player();
            domain.set_playerName(d_playerName);
        repository.save(domain);
            return "player " + d_playerName + " joined the game";
        }

    }

    public String remove(String d_playerName) {
        Optional<Player> playerOptional = findByName(d_playerName);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            repository.delete(player);
            return "Player " + d_playerName + " has been removed";
        } else {
            return "Player " + d_playerName + " not found";
        }
    }


    public Optional<Player> findByName(String d_playerName) {
        return repository.findByName(d_playerName);
    }

    public void assignReinforcements() {
        List<Player> playerList = this.repository.getAllPlayers();

        for (Player player : playerList) {
            int reinforcementForPlayer = this.getReinformcentsForPlayer(player.get_playerName());
            player.setNumberOfReinforcements(reinforcementForPlayer);
        }
    }

    private int getReinformcentsForPlayer(String d_playerName) {
        return DEFAULT_REINFORCEMENT_NUMBER;
    }

    private void executeOrders() {
        List<Order> roundOrders = orders.get(currentRound);
        for(Order order : roundOrders) {
            this.executeOrder(order);
        }
    }

    private void executeOrder(Order order) {
        if(order instanceof DeployOrder){
            // Add the reinforcements to the country
        }
    }
}
