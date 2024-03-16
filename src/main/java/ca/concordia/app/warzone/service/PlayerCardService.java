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
    private static final String bombCard = "bomb card";
    private static final String diplomacyCard = "diplomacy card";

    private static final String blockadeCard = "blockade card";

    private static final String airliftCard = "airlift card";

    private final ArrayList<String> playerCards;

    private final PlayerService d_playerService;



    /**
     * Data member for storing the ContinentRepository, for fetching and storing
     * continents.
     */
    private final ContinentRepository d_repoContinent; // Data member for the ContinentRepository

    /**
     * Constructor for PlayerCardsService.
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

    public void newContinentConquered(){
        List<Player> players = d_playerService.getAllPlayers();
        for(Player player : players){
            findNewContinents(player.getCountriesAssigned());
        }
    }

    public void findNewContinents(List<Country> countriesAssigned){
        Map<String, List<Country>> countriesByContinent = new HashMap<>();
        List<Continent> allContinents = d_repoContinent.findAll();

        for(Country country : countriesAssigned){
            String continentId = country.getContinent().getId();

            List<Country> countriesForContinent = countriesByContinent.getOrDefault(continentId, new ArrayList<>());
            countriesForContinent.add(country);

            countriesByContinent.put(continentId, countriesForContinent);
        }

        for(Continent continent: allContinents){
            String continentID  = continent.getId();
            int sizeOfContinent = continent.getSizeOfContinent();

            List<Country> countriesForContinent = countriesByContinent.getOrDefault(continentID, new ArrayList<>());

            if(countriesForContinent.size() == sizeOfContinent){
                //player.addContinentConquered
            }
        }
    }

}
