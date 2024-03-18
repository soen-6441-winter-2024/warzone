package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.service.CountryService;

/**
 * Represents a deploy order in the game, where a player deploys a certain number of armies to a country.
 */
public class DeployOrder extends Order {
    /**
     * The ID of the country to deploy armies to.
     */
    private String countryId;

    /**
     * The country service
     */
    private CountryService d_countryService;

    /**
     * The number of armies to deploy.
     */
    private int number;

    /**
     * Constructs a DeployOrder object with the specified player, country ID, and number of armies.
     *
     * @param player    The player issuing the deploy order.
     * @param countryId The ID of the country to deploy armies to.
     * @param number    The number of armies to deploy.
     * @param p_countryService country service
     */
    public DeployOrder(String player, String countryId, int number, CountryService p_countryService) {
        super(player);
        this.countryId = countryId;
        this.number = number;
        this.d_countryService = p_countryService;
    }

    /**
     * Retrieves the ID of the country to deploy armies to.
     *
     * @return The country ID.
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the country to deploy armies to.
     *
     * @param countryId The country ID to set.
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * Retrieves the number of armies to deploy.
     *
     * @return The number of armies.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of armies to deploy.
     *
     * @param number The number of armies to set.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Executes the logic for the type of order.
     */
    @Override
    public void execute() {
        System.out.println("Adding " + number + " armies to country " + countryId);
        d_countryService.addArmiesToCountry(countryId, number);
    }
}

