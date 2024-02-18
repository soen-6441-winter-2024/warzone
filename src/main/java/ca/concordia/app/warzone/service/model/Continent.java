package ca.concordia.app.warzone.service.model;
/**
 * Represents a continent in the game.
 */
public class Continent {

    private String d_id; // Data member for continent ID
    private String d_value; // Data member for continent value
    private int d_bonusArmies; // Data member for bonus armies

    /**
     * Retrieves the ID of the continent.
     *
     * @return the ID of the continent
     */
    public String getId() {
        return d_id;
    }

    /**
     * Sets the ID of the continent.
     *
     * @param id the ID of the continent
     */
    public void setId(String id) {
        this.d_id = id;
    }

    /**
     * Sets the bonus armies of the continent.
     *
     * @param bonusArmies the bonus armies of the continent
     */
    public void SetBonusArmies(int bonusArmies) {
        this.d_bonusArmies = bonusArmies;
    }

    /**
     * Gets the bonus armies of the continent.
     *
     * @param bonusArmies the bonus armies of the continent
     */
    public int GetBonusArmies() {
        return d_bonusArmies;
    }

    /**
     * Retrieves the value of the continent.
     *
     * @return the value of the continent
     */
    public String getValue() {
        return d_value;
    }

    /**
     * Sets the value of the continent.
     *
     * @param value the value of the continent
     */
    public void setValue(String value) {
        this.d_value = value;
    }
}
