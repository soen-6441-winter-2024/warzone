package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.model.Country;
import java.util.Optional;

/**
 * Represents the blockade order
 */
public class BlockadeOrder extends Order {
    private final String d_country;

    final private CountryService d_countryService;

    /**
     * Creates a blockade order
     *
     * @param p_player player that issued the order
     * @param p_country country
     * @param p_countryService country service
     */
    public BlockadeOrder(String p_player, String p_country, CountryService p_countryService) {
        super(p_player);
        d_countryService = p_countryService;
        d_country = p_country;
    }


    @Override
    public void execute() {
        Optional<Country> countryOptional = this.d_countryService.findCountryById(this.d_country);
        if(countryOptional.isEmpty()){
            return;
        }

        int currentArmies = countryOptional.get().getArmiesCount();
        countryOptional.get().setArmiesCount(currentArmies * 3);
    }
}
