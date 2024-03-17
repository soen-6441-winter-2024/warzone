package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.service.CountryService;

import java.util.Optional;

public class AdvanceOrder extends Order {
    public String getD_countryFrom() {
        return d_countryFrom;
    }

    public void setD_countryFrom(String d_countryFrom) {
        this.d_countryFrom = d_countryFrom;
    }

    public String getD_countryTo() {
        return d_countryTo;
    }

    public void setD_countryTo(String d_countryTo) {
        this.d_countryTo = d_countryTo;
    }

    public int getD_number() {
        return d_number;
    }

    public void setD_number(int d_number) {
        this.d_number = d_number;
    }

    private String d_countryFrom;
    private String d_countryTo;
    private int d_number;

    final private CountryService d_countryService;



    public AdvanceOrder(String p_player, String p_countryFrom, String p_countryTo, int p_number, CountryService p_countryService) {
        super(p_player);
        this.d_countryFrom = p_countryFrom;
        this.d_countryTo = p_countryTo;
        this.d_number = p_number;
        d_countryService = p_countryService;
    }


    @Override
    public void execute() {
        this.d_countryService.removeArmiesFromCountry(this.d_countryFrom, this.d_number);

        // TODO: check of countryFrom has available armies


        // Get countryTo and countryFrom

        // Calculate battle

        // Update armies count on countryTo


    }
}
