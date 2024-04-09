package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Player;
import java.util.List;
/**
 * Abstract class representing the strategy for a human player in the game.
 */

public abstract class HumanStrategy {

    /**
     * Constructs a HumanStrategy with the specified PlayerRepository.
     *
     */
    public HumanStrategy() {

    }

    /**
     * Adds an advance order.
     *
     * @param p_countryFrom      the source country for the advance
     * @param p_countryTo        the target country for the advance
     * @param armiesQuantity     the number of armies to advance
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn           the current turn in the game
     * @param p_diplomacyList    the list of diplomacy contracts between players
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addAdvanceOrder(String p_countryFrom, String p_countryTo, int armiesQuantity,
                                           int p_playerGivingOrder, int gameTurn, List<List<String>> p_diplomacyList);

    /**
     * Adds an airlift order.
     *
     * @param p_countryFrom      the source country for the airlift
     * @param p_countryTo        the target country for the airlift
     * @param armiesQuantity     the number of armies to airlift
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn           the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addAirliftOrder(String p_countryFrom, String p_countryTo, int armiesQuantity,
                                           int p_playerGivingOrder, int gameTurn);

    /**
     * Adds a blockade order.
     *
     * @param p_country           the country to blockade
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addBlockadeOrder(String p_country, int p_playerGivingOrder, int gameTurn);

    /**
     * Adds a deploy order.
     *
     * @param p_countryId         the ID of the country to deploy armies to
     * @param p_numberOfReinforcements the number of armies to deploy
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return an empty string if successful, otherwise an error message
     */
    public abstract String addDeployOrder(String p_countryId, int p_numberOfReinforcements,
                                          int p_playerGivingOrder, int gameTurn);

    /**
     * Adds a bomb order.
     *
     * @param p_targetCountryId   the ID of the country to bomb
     * @param p_playerGivingOrder the player issuing the order
     * @param gameTurn            the current turn in the game
     * @return a message indicating the success or failure of the operation
     */
    public abstract String addBombOrder(String p_targetCountryId, int p_playerGivingOrder, int gameTurn);

    /**
     * Retrieves a list of all players.
     *
     * @return a list of all players
     */
    public abstract List<Player> getAllPlayers();

    /**
     * Prompts the specified player to give a deploy order.
     *
     * @param p_playerGivingOrder the index of the player to prompt
     */
    public abstract void askForDeployOrder(int p_playerGivingOrder);
}