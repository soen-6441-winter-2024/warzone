package ca.concordia.app.warzone.model;

import java.util.List;

/**
 * Represents a Map File in the game.
 */
public class MapFile {
    private String d_FileName;

    private List<Continent> d_continents;

    private List<Country> d_countries;

    /**
     * Default constructor
     */
    public MapFile() {

    }
    /**
     * Gets the file name of the map.
     *
     * @return The file name of the map.
     */
    public String getFileName() {
        return d_FileName;
    }

    /**
     * Sets the file name of the map.
     *
     * @param fileName The file name of the map.
     */
    public void setFileName(String fileName) {
        this.d_FileName = fileName;
    }

    /**
     * Get continents from the map
     *
     * @return continent list
     */
    public List<Continent> getContinents() {
        return d_continents;
    }

    /**
     * Set continents to the map
     *
     * @param d_continents continent list
     */
    public void setContinents(List<Continent> d_continents) {
        this.d_continents = d_continents;
    }

    /**
     * Get countries from the map
     *
     * @return country list
     */
    public List<Country> getCountries() {
        return d_countries;
    }

    /**
     * Set countries to the map
     *
     * @param d_countries country list
     */
    public void setCountries(List<Country> d_countries) {
        this.d_countries = d_countries;
    }
}
