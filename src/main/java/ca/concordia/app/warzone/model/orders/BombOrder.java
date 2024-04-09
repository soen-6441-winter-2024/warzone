package ca.concordia.app.warzone.model.orders;

import java.util.List;
import java.util.Optional;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.service.CountryService;

/**
 * Represents the bomb order
 */
public class BombOrder extends Order {
    private CountryService d_countryService;
    private String d_targetCountry;
    private String d_player;

    /**
     * Bomb order constructor
     * 
     * @param player           player that issued the order
     * @param p_CountryService country service
     * @param p_targetCountry  target country
     */
    public BombOrder(String player, CountryService p_CountryService, String p_targetCountry) {
        super(player);
        this.d_player = player;
        this.d_countryService = p_CountryService;
        this.d_targetCountry = p_targetCountry;
    }

    @Override
    public void execute() {
        List<Country> countries = this.d_countryService.findAll();
        
        Optional<CountryDto> countryOptional = this.d_countryService.findById(this.d_targetCountry);
        // destroy half of the armies located on an opponent’s territory that is adjacent to any one of the current player’s territories.
        
        for (Country country : countries) {
            if(country.getPlayer().get().getId().equals(player)) {
                for (Country neighbor : country.getNeighbors()) {
                    if(this.d_targetCountry.equals(neighbor.getId())) {
                        this.d_countryService.bombACountry(d_targetCountry);
                        System.out.println("Country " + this.d_targetCountry + " Bombed by player " + d_player);
                        return;
                    }
                }
            }
        }

        this.d_countryService.bombACountry(d_targetCountry);
    }

}
