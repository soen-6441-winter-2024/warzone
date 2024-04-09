package ca.concordia.app.warzone.model;

import java.util.ArrayList;
import java.util.List;
import ca.concordia.app.warzone.model.Strategies.HumanStrategy;

/**
 * Represents a player in the game.
 */
public class Player implements Entity {
    private static int d_lastPlayerID = 0; // Static member for the last player ID
    private String d_playerName; // Data member for player name
    private List<Country> d_countriesAssigned; // Data member for countries assigned to the player
    /**
     * A list of player's order for each round of the game.
     */
    private List<List<Order>> d_playerOrders;

    /**
     * List of continents
     */
    private List<String> d_continent;
    /**
     *  Data member for cards received by player
     */
    public List<String> d_cardsReceived;
    /**
     * The number of reinforcement armies available to a player each round of the game
     */
    private int numberOfReinforcementsAvailable;

    /**
     * The strategy that will be adopted by the Human player during the game
     */
    private HumanStrategy d_humanStrategy;

    /**
     * Default constructor for Player.
     * Initializes player ID and countries assigned list.
     */
    public Player(){
        this.d_countriesAssigned = new ArrayList<>();
        this.d_playerOrders = new ArrayList<>();
        this.d_cardsReceived = new ArrayList<>();
        this.d_continent = new ArrayList<>();
    }

    /**
     * Constructor for Human Player with strategy.
     * Initializes player ID, countries assigned list, and strategy.
     *
     * @param p_humanStrategy the strategy to set
     */
    public Player(HumanStrategy p_humanStrategy) {
        this();
        this.d_humanStrategy = p_humanStrategy;
    }

    /**
     * Adds a country to the list of countries assigned to the player.
     *
     * @param p_country the country to add
     */
    public void addCountry(Country p_country){
        d_countriesAssigned.add(p_country);
    }

    /**
     * Add Continent conquered
     *
     * @param p_continent continent id
     */
    public void addContinentConquered(String p_continent){
        d_continent.add(p_continent);
    }

    /**
     * Get list of continents
     *
     * @return list of continents
     */
    public List<String> getContinents(){
        return d_continent;
    }

    /**
     * Method that adds card to list of cards
     * @param card the card to be added
     */
    public void addCard(String card){
        d_cardsReceived.add(card);
    }

    /**
     * Method that removes used card from players list of cards
     * @param card the card to be added
     */
    public void removeUsedCard(String card){
        d_cardsReceived.remove(card);
    }

    /**
     * Checks if a player has a card
     * @param card the card to be checked
     * @return returns boolean whether player has card or not
     */
    public boolean hasCard(String card){
        return d_cardsReceived.contains(card);
    }

    public List<String> getCards(){
        return d_cardsReceived;
    }
    /**
     * Retrieves the player name.
     *
     * @return the player name
     */
    public String getPlayerName(){
        return d_playerName;
    }

    /**
     * Sets the player name.
     *
     * @param p_playerName the player name to set
     */
    public void setPlayerName(String p_playerName){
        this.d_playerName = p_playerName;
    }

    /**
     * Retrieves the list of countries assigned to the player.
     *
     * @return the list of countries assigned to the player
     */
    public List<Country> getCountriesAssigned(){
        return d_countriesAssigned;
    }

    /**
     * Removes a country assigned to a player if they lose the country
     * @param countryId the id of the country to remove
     */
    public void removeAssignedCountry(String countryId) {
        for (int i = 0; i < d_countriesAssigned.size(); i++) {
            if(d_countriesAssigned.get(i).equals(countryId)) {
                d_countriesAssigned.remove(i);
            }
        }
    }

    /**
     * Sets the list of countries assigned to the player.
     *
     * @param p_countriesAssigned the list of countries assigned to the player
     */
    public void setCountriesAssigned(List<Country> p_countriesAssigned){
        this.d_countriesAssigned = p_countriesAssigned;
    }

    /**
     * Removes a country from the list of countries owned by the player.
     *
     * @param country the country to remove
     */
    public void  removeCountry(Country country){
        d_countriesAssigned.remove(country);
    }

    /**
     * Adds a newly conquered country to the list of countries owned by the player.
     *
     * @param p_newlyConqueredCountry the country to add
     */
    public void addNewConqueredCountry(Country p_newlyConqueredCountry) {
        this.d_countriesAssigned.add(p_newlyConqueredCountry);
    }

    /**
     * Returns the number of reinforcement armies available to the player.
     *
     * @return the number of reinforcement armies available
     */
    public int getNumberOfReinforcements() {
        return numberOfReinforcementsAvailable;
    }

    /**
     * Sets the number of reinforcement armies available to the player.
     *
     * @param p_numberOfReinforcements the new number of reinforcement armies available
     */
    public void setNumberOfReinforcements(int p_numberOfReinforcements) {
        this.numberOfReinforcementsAvailable = p_numberOfReinforcements;
    }

    /**
     * Checks if the player owns a given country.
     *
     * @param p_countryId the ID of the country to check
     * @return true if the player owns the country, false otherwise
     */
    public boolean ownsCountry(String p_countryId) {
        for(Country currentCountry : this.d_countriesAssigned) {
            if(currentCountry.getId().equals(p_countryId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getId() {
        return this.d_playerName;
    }

    /**
     * Set id of the player, which is also the name
     *
     * @param p_id player name
     */
    public void setId(String p_id) {
        this.d_playerName = p_id;
    }

    /**
     * Returns the list of orders the player has issued for the current turn.
     *
     * @param currentTurn the current turn
     * @return the list of orders
     */
    public List<Order> getPlayerCurrentTurnOrders(int currentTurn) {
        return this.d_playerOrders.get(currentTurn);
    }

    /**
     * Sets the strategy of the Human player.
     *
     * @param p_humanStrategy the strategy of the player
     */
    public void setHumanStrategy(HumanStrategy p_humanStrategy) {
        this.d_humanStrategy = p_humanStrategy;
    }

    /**
     * Sets the list of orders a player has issued in a turn
     * @param order order to be issued
     * @param round turn
     */
    public void issueOrder(Order order, int round) {
        if(round == this.d_playerOrders.size()) {
            this.d_playerOrders.add(new ArrayList<>());
        }
        this.d_playerOrders.get(round).add(order);
    }

    /**
     * Issues an advance order using the human player's strategy.
     *
     * @param p_countryFrom     origin country
     * @param p_countryTo       destination country
     * @param armiesQuantity    quantity of armies
     * @param p_playerGivingOrder player issuing the order
     * @param gameTurn          current turn
     * @param p_diplomacyList   list of diplomacy
     * @return result message
     */
    public String addAdvanceOrder(String p_countryFrom, String p_countryTo, int armiesQuantity,
                                  int p_playerGivingOrder, int gameTurn, List<List<String>> p_diplomacyList) {
        return d_humanStrategy.addAdvanceOrder(p_countryFrom, p_countryTo, armiesQuantity, p_playerGivingOrder, gameTurn, p_diplomacyList);
    }

    /**
     * Issues an airlift order using the human player's strategy.
     *
     * @param p_countryFrom     origin country
     * @param p_countryTo       destination country
     * @param armiesQuantity    quantity of armies
     * @param p_playerGivingOrder player issuing the order
     * @param gameTurn          current turn
     * @return result message
     */
    public String addAirliftOrder(String p_countryFrom, String p_countryTo, int armiesQuantity, int p_playerGivingOrder, int gameTurn) {
        return d_humanStrategy.addAirliftOrder(p_countryFrom, p_countryTo, armiesQuantity, p_playerGivingOrder, gameTurn);
    }

    /**
     * Issues a blockade order using the human player's strategy.
     *
     * @param p_country         country to blockade
     * @param p_playerGivingOrder player issuing the order
     * @param gameTurn          current turn
     * @return result message
     */
    public String addBlockadeOrder(String p_country, int p_playerGivingOrder, int gameTurn) {
        return d_humanStrategy.addBlockadeOrder(p_country, p_playerGivingOrder, gameTurn);
    }

    /**
     * Issues a deploy order using the human player's strategy.
     *
     * @param p_countryId           country to deploy armies to
     * @param p_numberOfReinforcements quantity of armies to deploy
     * @param p_playerGivingOrder player issuing the order
     * @param gameTurn              current turn
     * @return result message
     */
    public String addDeployOrder(String p_countryId, int p_numberOfReinforcements,
                                 int p_playerGivingOrder, int gameTurn) {
        return d_humanStrategy.addDeployOrder(p_countryId, p_numberOfReinforcements, p_playerGivingOrder, gameTurn);
    }

    /**
     * Issues a bomb order using the human player's strategy.
     *
     * @param p_targetCountryId    country to bomb
     * @param p_playerGivingOrder player issuing the order
     * @param gameTurn             current turn
     * @return result message
     */
    public String addBombOrder(String p_targetCountryId, int p_playerGivingOrder, int gameTurn) {
        return d_humanStrategy.addBombOrder(p_targetCountryId, p_playerGivingOrder, gameTurn);
    }

}
