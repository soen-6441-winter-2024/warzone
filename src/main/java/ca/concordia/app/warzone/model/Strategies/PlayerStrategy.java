package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;

import java.util.List;

/**
 * Abstract class representing the strategy for a computer player in the game.
 */
public abstract class PlayerStrategy {
    public Player d_player;

    private List<Country> d_countriesAssigned;


    /**
     * Constructs a ComputerStrategy with the specified player and list of assigned countries.
     * @param d_player  the player associated with this strategy
     * @param d_countriesAssigned the list of countries assigned to the player
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
     *
     */
    public abstract Country attackCountry(Country p_country);

    /**
     * Selects the country from which to launch an attack.
     *
     *
     */
    public abstract Country countryToAttackFrom();
}
