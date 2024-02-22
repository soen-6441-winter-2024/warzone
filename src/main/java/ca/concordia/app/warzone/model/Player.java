package ca.concordia.app.warzone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
    private static int d_lastPlayerID = 0; // Static member for the last player ID
    private int d_playerID; // Data member for player ID
    private String d_playerName; // Data member for player name
    private List<Country> d_countriesAssigned; // Data member for countries assigned to the player
    private int numberOfReinforcementsAvailable;

    /**
     * Default constructor for Player.
     * Initializes player ID and countries assigned list.
     */
    public Player(){
        this.d_playerID = d_lastPlayerID + 1;
        this.d_countriesAssigned = new ArrayList<>();
    }

    /**
     * Retrieves the player ID.
     *
     * @return the player ID
     */
    public int getPlayerID(){
        return d_playerID;
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
}
