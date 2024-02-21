package ca.concordia.app.warzone.model;

/**
 * Represents a deploy order in the game, where a player deploys a certain number of armies to a country.
 */
public class DeployOrder extends Order {

    /**
     * The ID of the country to deploy armies to.
     */
    private String countryId;

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
     */
    public DeployOrder(String player, String countryId, int number) {
        super(player);
        this.countryId = countryId;
        this.number = number;
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
}

