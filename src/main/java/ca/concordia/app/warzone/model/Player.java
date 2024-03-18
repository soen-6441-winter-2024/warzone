package ca.concordia.app.warzone.model;

import java.util.ArrayList;
import java.util.List;

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
     *method that adds card to list of cards
     * @param card the card to be added
     */
    public void addCard(String card){
        d_cardsReceived.add(card);
    }

    /**
     *method that removes used card from players list of cards
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
     * Sets the list of countries assigned to the player.
     *
     * @param p_countriesAssigned the list of countries assigned to the player
     */
    public void setCountriesAssigned(List<Country> p_countriesAssigned){
        this.d_countriesAssigned = p_countriesAssigned;
    }

    /**
     * Returns the number of reinforcement this player has available
     * @return the number of reinforcement armies available
     */
    public int getNumberOfReinforcements() {
        return numberOfReinforcementsAvailable;
    }

    /**
     * Sets the number of reinforcement available
     * @param p_numberOfReinforcements the new number of reinforcements available
     */
    public void setNumberOfReinforcements(int p_numberOfReinforcements) {
        this.numberOfReinforcementsAvailable = p_numberOfReinforcements;
    }

    /**
     * Returns whether a player owns a given country
     * @param p_countryId The countryId
     * @return true if the player owns the given country, false otherwise
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
     * Returns the list of orders player has issue for the currentTurn
     * @param currentTurn turn
     * @return list of orders
     */
    public List<Order> getPlayerCurrentTurnOrders(int currentTurn) {
        return this.d_playerOrders.get(currentTurn);
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
}
