package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.ContinentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A service class that manages player cards in a Warzone game.
 * This class provides method for assigning player cards.
 */
@Service
public class PlayerCardService {
    private static final String bombCard = "bomb_card";
    private static final String diplomacyCard = "diplomacy_card";

    private static final String blockadeCard = "blockade_card";

    private static final String airliftCard = "airlift_card";

    private final ArrayList<String> playerCards;

    private final PlayerService d_playerService;



    /**
     * Data member for storing the ContinentRepository, for fetching and storing
     * continents.
     */
    private final ContinentRepository d_repoContinent; // Data member for the ContinentRepository

    /**
     * Constructor for PlayerCardsService.
     * @param d_PlayerService playerservice to be used
     * @param d_repoContinent continent repository to be used
     */
    public PlayerCardService(PlayerService d_PlayerService, ContinentRepository d_repoContinent) {
        this.d_playerService = d_PlayerService;
        this.d_repoContinent = d_repoContinent;
        playerCards = new ArrayList<>();
        playerCards.add(bombCard);
        playerCards.add(diplomacyCard);
        playerCards.add(blockadeCard);
        playerCards.add(airliftCard);
    }

    /**
     * Assigns a card to player.
     * @param player the player getting a card.
     */
    public void assignPlayerCards(Player player){
        Collections.shuffle(playerCards);
        player.addCard(playerCards.get(0));
    }

    /**
     *
     * @param sizeBeforeLoop map containing size of the countries assigned before each loop
     * @param sizeAfterLoop map containing size of the countries assigned after each loop
     */
    public void newCountryConquered(Map<String, Integer> sizeBeforeLoop, Map<String, Integer> sizeAfterLoop){
        List<Player> players = d_playerService.getAllPlayers();
        for(Map.Entry<String, Integer> entry : sizeBeforeLoop.entrySet()){
            String playerName = entry.getKey();
            int sizeBefore = entry.getValue();
            int sizeAfter = sizeAfterLoop.getOrDefault(playerName, 0);
            if(sizeAfter > sizeBefore){
                for(Player player : players){
                    if(player.getPlayerName().equals(playerName)){
                        for(int i = 0; i < sizeAfter - sizeBefore; i++){
                            assignPlayerCards(player);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * gets size of countries assigned
     *
     * @return the size of the countryassigned
     */
    public Map<String, Integer> getSizeOfCountriesAssigned(){
        Map<String, Integer> sizeOfCountriesAssigned = new HashMap<>();
        List<Player> players = d_playerService.getAllPlayers();
        for(Player player : players){
            sizeOfCountriesAssigned.put(player.getPlayerName(), player.getCountriesAssigned().size());
        }

        return sizeOfCountriesAssigned;
    }

}
