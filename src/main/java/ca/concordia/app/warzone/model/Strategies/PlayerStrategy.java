package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;

import java.util.List;

/**
 * Represents a player strategy for handling the logic of the game
 */
public abstract class PlayerStrategy {
    /**
     * The player
     */
    public Player d_player;

    /**
     * Assigned countries of the Player
     */
    private List<Country> d_countriesAssigned;


    /**
     * Constructor
     * @param d_player the player
     * @param d_countriesAssigned the countries assigned
     */
    public PlayerStrategy(Player d_player, List<Country> d_countriesAssigned) {
        this.d_player = d_player;
        this.d_countriesAssigned = d_countriesAssigned;
    }


    /**
     * Creates orders based on the strategy.
     *
     * @return a string representing the created orders
     */
    public abstract String createOrder();

    /**
     * Selects the country to attack.
     * @param p_country the country
     * @return p_country the country
     */
    public abstract Country attackCountry(Country p_country);

    /**
     * Selects the country from which to launch an attack.
     * @return the country
     */
    public abstract Country countryToAttackFrom();
}
