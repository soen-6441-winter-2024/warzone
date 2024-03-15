package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A service class that manages player cards in a Warzone game.
 * This class provides method for assigning player cards
 */
@Service
public class PlayerCardService {
    private static final String bombCard = "bomb card";
    private static final String diplomacyCard = "diplomacy card";

    private static final String blockadeCard = "blockade card";

    private static final String airliftCard = "airlift card";

    private final ArrayList<String> playerCards;

    /**
     * Constructor for PlayerCardsService
     */
    public PlayerCardService() {
        playerCards = new ArrayList<>();
        playerCards.add(bombCard);
        playerCards.add(diplomacyCard);
        playerCards.add(blockadeCard);
        playerCards.add(airliftCard);
    }

    /**
     * Assigns a card to player
     * @param player the player getting a card
     */
    public void assignPlayerCards(Player player){
        Collections.shuffle(playerCards);
        player.addCard(playerCards.get(0));
    }

}
