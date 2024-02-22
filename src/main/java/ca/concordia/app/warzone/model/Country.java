package ca.concordia.app.warzone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a country in the game.
 */
public class Country {

    private String d_id; // Data member for country ID
    private Continent d_continent; // Data member for continent
    private List<Country> d_neighbors; // Data member for neighboring countries
    private Optional<Player> d_player; // Data member for player, wrapped in Optional

    private int d_armiesCount;

    /**
     * Default constructor for Country.
     * Initializes neighbors list as an empty ArrayList and player as an empty Optional.
     */
    public Country() {
        this.d_neighbors = new ArrayList<>();
        this.d_player = Optional.empty();
        this.d_armiesCount = 0;
    }

    /**
     * Retrieves the ID of the country.
     *
     * @return the ID of the country
     */
    public String getId() {
        return d_id;
    }

    /**
     * Sets the ID of the country.
     *
     * @param id the ID of the country
     */
    public void setId(String id) {
        this.d_id = id;
    }

    /**
     * Retrieves the continent of the country.
     *
     * @return the continent of the country
     */
    public Continent getContinent() {
        return d_continent;
    }

    /**
     * Sets the continent of the country.
     *
     * @param continent the continent of the country
     */
    public void setContinent(Continent continent) {
        this.d_continent = continent;
    }

    /**
     * Retrieves the list of neighboring countries.
     *
     * @return the list of neighboring countries
     */
    public List<Country> getNeighbors() {
        return d_neighbors;
    }

    /**
     * Sets the list of neighboring countries.
     *
     * @param neighbors the list of neighboring countries
     */
    public void setNeighbors(List<Country> neighbors) {
        this.d_neighbors = neighbors;
    }

    /**
     * Retrieves the player of the country.
     *
     * @return the player of the country, wrapped in Optional
     */
    public Optional<Player> getPlayer() {
        return d_player;
    }

    /**
     * Sets the player of the country.
     *
     * @param player the player of the country, wrapped in Optional
     */
    public void setPlayer(Optional<Player> player) {
        this.d_player = player;
    }

    /**
     * Add a neighbor to the country
     *
     * @param country the neighbor to be added, must not be null
     */
    public void addNeighbor(Country country) {
        this.d_neighbors.add(country);
    }

    /**
     * Returns the number of armies deployed on this country
     * @return the armies count
     */
    public int getArmiesCount() {
        return d_armiesCount;
    }

    /**
     * Sets the armies count
     * @param p_armiesCount the number of armies deployed on this country
     */
    public void setArmiesCount(int p_armiesCount) {
        this.d_armiesCount = p_armiesCount;
    }


    @Override
    public String toString() {
        return this.getId();
    }
}
