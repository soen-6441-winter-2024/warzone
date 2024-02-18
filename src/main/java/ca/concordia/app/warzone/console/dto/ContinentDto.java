package ca.concordia.app.warzone.console.dto;

/**
 * Represents a DTO (Data Transfer Object) for a continent.
 */
public class ContinentDto {

    /** The ID of the continent. */
    private String d_Id;

    /** The value/name of the continent. */
    private String d_Value;

    /** The number of bonus armies a player gets if he/she controls the whole continent */
    private int d_bonusArmies;

    /**
     * Gets the ID of the continent.
     *
     * @return The ID of the continent.
     */
    public String getId() {
        return d_Id;
    }
    /**
     * Get the number bonus armies
     * @return The number of bonus armies a player gets if he/she controls the whole continent
     */
    public int get_bonusArmies() {
        return d_bonusArmies;
    }
    /**
     * Set the number bonus armies
     * @param d_bonusArmies
     */
    public void set_bonusArmies(int d_bonusArmies) {
        this.d_bonusArmies = d_bonusArmies;
    }

    /**
     * Sets the ID of the continent.
     *
     * @param id The ID of the continent.
     */
    public void setId(String id) {
        this.d_Id = id;
    }

    /**
     * Gets the value/name of the continent.
     *
     * @return The value/name of the continent.
     */
    public String getValue() {
        return d_Value;
    }

    /**
     * Sets the value/name of the continent.
     *
     * @param value The value/name of the continent.
     */
    public void setValue(String value) {
        this.d_Value = value;
    }

}
