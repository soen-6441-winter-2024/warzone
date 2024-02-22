package ca.concordia.app.warzone.model;
/**
 * Represents a continent in the game.
 */
public class Continent {

    private String d_id; // Data member for continent ID
    private String d_value; // Data member for continent value

    /**
     * Default constructor
     */
    public Continent() {

    }
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
