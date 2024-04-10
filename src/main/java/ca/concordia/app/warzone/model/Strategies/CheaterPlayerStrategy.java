package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the strategy for a cheater computer player in the game.
 */
public class CheaterPlayerStrategy extends PlayerStrategy {
    /**
     *
     * Constructor
     * @param d_player the player
     * @param d_countriesAssigned the list of countries assigned to the player
     */
    public CheaterPlayerStrategy(Player d_player, List<Country> d_countriesAssigned) {
        super(d_player, d_countriesAssigned);
    }

    /**
     * @return null
     */
    @Override
    public Country attackCountry(Country p_currentCountryToAttackFrom) {
        return null;
    }

    /**
     * @return null
     */
    @Override
    public Country countryToAttackFrom() {
        return null;
    }

    /**
     * Creates orders based on the strategy.
     *
     * @return a string representing the created orders
     */
    @Override
    public String createOrder() {
        List<Country> p_countrieslist = d_player.getCountriesAssigned();

        for(Country p_countriesassigned : p_countrieslist){
            int doubleArmy = p_countriesassigned.getArmiesCount() * 2;
            p_countriesassigned.setArmiesCount(doubleArmy);
            for(Country p_neighborCountry : p_countriesassigned.getNeighbors()){
                Optional<Player> optionalplayer = p_neighborCountry.getPlayer();

                if(optionalplayer.isPresent()){
                    Player neighborplayer = optionalplayer.get();
                    String neighborplayerId = neighborplayer.getId();
                    if(!Objects.equals(neighborplayerId, d_player.getId())){
                        neighborplayer.removeCountry(p_neighborCountry);
                        d_player.addCountry(p_neighborCountry);
                        p_neighborCountry.setPlayer(Optional.of(d_player));
                    }
                }
            }

        }

        return null;
    }
}
