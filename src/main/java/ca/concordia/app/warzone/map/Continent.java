package ca.concordia.app.warzone.map;


/**
 * Represents a continent in the Warzone map.
 */
public class Continent {
    private String d_name;
    private int d_id;
    private int d_bonusArmies;

    /**
     * Constructs a new Continent with the specified name, id, and bonus armies.
     *
     * @param p_name        the name of the continent
     * @param p_id          the unique identifier of the continent
     * @param p_bonusArmies the bonus armies awarded for controlling the continent
     */
    public Continent(String p_name, int p_id, int p_bonusArmies) {
        this.d_name = p_name;
        this.d_id = p_id;
        this.d_bonusArmies = p_bonusArmies;
    }

    /**
     * Returns the name of the continent.
     *
     * @return the name of the continent
     */
    public String getName() {
        return d_name;
    }

    /**
     * Returns the unique identifier of the continent.
     *
     * @return the unique identifier of the continent
     */
    public int getId() {
        return d_id;
    }

    /**
     * Returns the bonus armies awarded for controlling the continent.
     *
     * @return the bonus armies awarded for controlling the continent
     */
    public int getBonusArmies() {
        return d_bonusArmies;
    }
}
