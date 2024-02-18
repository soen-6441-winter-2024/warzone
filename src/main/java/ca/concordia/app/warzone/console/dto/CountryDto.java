package ca.concordia.app.warzone.console.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a DTO (Data Transfer Object) for a country.
 */
public class CountryDto {

    /** The ID of the country. */
    private String d_Id;

    /** The ID of the neighboring country. */
    private String d_NeighborId;

    /** The continent of the country. */
    private ContinentDto d_Continent;

    /** The list of neighboring countries. */
    private List<CountryDto> d_Neighbors;

    /** The player associated with the country. */
    private PlayerDto d_Player;

    /**
     * Default constructor for CountryDto.
     * Initializes the list of neighbors.
     */
    public CountryDto() {
        this.d_Neighbors = new ArrayList<>();
    }

    /**
     * Gets the ID of the country.
     *
     * @return The ID of the country.
     */
    public String getId() {
        return d_Id;
    }

    /**
     * Sets the ID of the country.
     *
     * @param id The ID of the country.
     */
    public void setId(String id) {
        this.d_Id = id;
    }

    /**
     * Gets the ID of the neighboring country.
     *
     * @return The ID of the neighboring country.
     */
    public String getNeighborId() {
        return d_NeighborId;
    }

    /**
     * Sets the ID of the neighboring country.
     *
     * @param neighborId The ID of the neighboring country.
     */
    public void setNeighborId(String neighborId) {
        this.d_NeighborId = neighborId;
    }

    /**
     * Gets the continent of the country.
     *
     * @return The continent of the country.
     */
    public ContinentDto getContinent() {
        return d_Continent;
    }

    /**
     * Sets the continent of the country.
     *
     * @param continent The continent of the country.
     */
    public void setContinent(ContinentDto continent) {
        this.d_Continent = continent;
    }

    /**
     * Gets the list of neighboring countries.
     *
     * @return The list of neighboring countries.
     */
    public List<CountryDto> getNeighbors() {
        return d_Neighbors;
    }

    /**
     * Sets the list of neighboring countries.
     *
     * @param neighbors The list of neighboring countries.
     */
    public void setNeighbors(List<CountryDto> neighbors) {
        this.d_Neighbors = neighbors;
    }

    /**
     * Gets the player associated with the country.
     *
     * @return The player associated with the country.
     */
    public PlayerDto getPlayer() {
        return d_Player;
    }

    /**
     * Sets the player associated with the country.
     *
     * @param player The player associated with the country.
     */
    public void setPlayer(PlayerDto player) {
        this.d_Player = player;
    }
}
