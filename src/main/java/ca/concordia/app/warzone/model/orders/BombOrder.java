package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.service.CountryService;

/**
 * Represents the bomb order
 */
public class BombOrder extends Order {
    private CountryService d_countryService;
    private String d_targetCountry; 

    /**
     * Bomb order constructor
     * @param player player that issued the order
     * @param p_CountryService country service
     * @param p_targetCountry target country
     */
    public BombOrder(String player, CountryService p_CountryService, String p_targetCountry) {
        super(player);
        this.d_countryService = p_CountryService;
        this.d_targetCountry = p_targetCountry;
    }

    @Override
    public void execute() {
        this.d_countryService.bombACountry(d_targetCountry);
    }
    
}
