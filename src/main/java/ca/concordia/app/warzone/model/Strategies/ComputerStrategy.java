package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;

import java.util.List;

/**
 * Abstract class representing the strategy for a computer player in the game.
 */
public abstract class ComputerStrategy {
    /**
     * The player associated with this strategy.
     */
    public Player d_player;

    /**
     * The list of countries assigned to the player.
     */
    private List<Country> d_countriesAssigned;

    /**
     * Constructs a ComputerStrategy with the specified player and list of assigned countries.
     *
     * @param d_player          the player associated with this strategy
     * @param d_countriesAssigned the list of countries assigned to the player
     */
    public ComputerStrategy(Player d_player, List<Country> d_countriesAssigned) {
        this.d_player = d_player;
        this.d_countriesAssigned = d_countriesAssigned;
    }

    /**
     * Creates an order based on the strategy.
     *
     * @return a string representing the order created
     */
    public abstract String createOrder();

    /**
     * Selects a country to attack.
     * @param p_country the country
     * @return the country to attack
     */
    public abstract Country attackCountry(Country p_country);

    /**
     * Selects a country from which to launch an attack.
     *
     * @return the country from which to attack
     */
    public abstract Country countryToAttackFrom();
}