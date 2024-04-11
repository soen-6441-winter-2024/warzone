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
public class CheaterComputerPlayerStrategy extends ComputerStrategy {

    /**
     * Constructs a CheaterComputerPlayerStrategy with the specified parameters.
     *
     * @param d_player the player associated with this strategy
     * @param d_countriesAssigned the list of countries assigned to the player
     */
    public CheaterComputerPlayerStrategy(Player d_player, List<Country> d_countriesAssigned) {
        super(d_player, d_countriesAssigned);
    }

    /**
     * Determines the country to attack (not applicable for cheater strategy).
     * @param p_currentCountryToAttackFrom the current country to attack from
     * @return null (not applicable for cheater strategy)
     */
    @Override
    public Country attackCountry(Country p_currentCountryToAttackFrom) {
        return null;
    }

    /**
     * Determines the country from which to launch an attack (not applicable for cheater strategy).
     *
     * @return null (not applicable for cheater strategy)
     */
    @Override
    public Country countryToAttackFrom() {
        return null;
    }

    /**
     * Creates orders for the cheater strategy.
     *
     * <p>This method doubles the number of armies in each country owned by the player,
     * and moves all neighboring countries to be owned by the player as well.</p>
     *
     * @return null (not applicable for cheater strategy)
     */
    @Override
    public String createOrder() {
        List<Country> p_countrieslist = d_player.getCountriesAssigned();

        for(Country p_countriesassigned : p_countrieslist){
            int doubleArmy = p_countriesassigned.getArmiesCount() * 2;
            p_countriesassigned.setArmiesCount(doubleArmy);
            if(p_countriesassigned.getNeighbors() == null || p_countriesassigned.getNeighbors().size() == 0) {
                return null;
            }
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