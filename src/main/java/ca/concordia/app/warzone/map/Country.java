package ca.concordia.app.warzone.map;

/**
 * Represents a country in the Warzone map.
 */
public class Country {
    private int d_id;
    private int d_numberOfArmies;
    private String d_name;
    private String d_owner; // should preferably be a player object
    private int d_continentId;

    /**
     * Constructs a new country with the specified id, number of armies, name, and continent id.
     *
     * @param p_id             the unique identifier of the country
     * @param p_numberOfArmies the number of armies stationed in the country
     * @param p_name           the name of the country
     * @param p_continentId    the unique identifier of the continent to which the country belongs
     */
    public Country(int p_id, int p_numberOfArmies, String p_name, int p_continentId) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_numberOfArmies = p_numberOfArmies;
        this.d_continentId = p_continentId;
    }

    /**
     * Returns the unique identifier of the country.
     *
     * @return the unique identifier of the country
     */
    public int getId() {
        return d_id;
    }

    /**
     * Returns the number of armies stationed in the country.
     *
     * @return the number of armies stationed in the country
     */
    public int getNumberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * Returns the name of the country.
     *
     * @return the name of the country
     */
    public String getName() {
        return d_name;
    }

    /**
     * Returns the owner of the country.
     *
     * @return the owner of the country
     */
    public String getOwner() {
        return d_owner;
    }

    /**
     * Returns the unique identifier of the continent to which the country belongs.
     *
     * @return the unique identifier of the continent to which the country belongs
     */
    public int getContinentId() {
        return d_continentId;
    }

    /**
     * Sets the owner of the country.
     *
     * @param p_owner the owner of the country
     */
    public void setOwner(String p_owner) {
        this.d_owner = p_owner;
    }

    /**
     * Sets the number of armies stationed in the country.
     *
     * @param p_numberOfArmies the number of armies stationed in the country
     */
    public void setNumberOfArmies(int p_numberOfArmies) {
        this.d_numberOfArmies = p_numberOfArmies;
    }
}
