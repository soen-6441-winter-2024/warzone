package ca.concordia.app.warzone.console.dto;

import java.util.List;

/**
 * Represents a DTO (Data Transfer Object) for a player.
 */
public class PlayerDto {

    /**
     * Default constructor
     */
    public PlayerDto() {}
    /** The name of the player. */
    private String d_PlayerName;

    /** The list of countries assigned to the player. */
    private List<String> d_CountriesAssigned;

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * Sets the name of the player.
     *
     * @param playerName The name of the player.
     */
    public void setPlayerName(String playerName) {
        this.d_PlayerName = playerName;
    }

    /**
     * Gets the list of countries assigned to the player.
     *
     * @return The list of countries assigned to the player.
     */
    public List<String> getCountriesAssigned() {
        return d_CountriesAssigned;
    }

    /**
     * Sets the list of countries assigned to the player.
     *
     * @param countriesAssigned The list of countries assigned to the player.
     */
    public void setCountriesAssigned(List<String> countriesAssigned) {
        this.d_CountriesAssigned = countriesAssigned;
    }
}
