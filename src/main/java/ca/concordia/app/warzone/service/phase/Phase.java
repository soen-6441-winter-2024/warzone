package ca.concordia.app.warzone.service.phase;


import java.util.List;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;

/**
 * Represents the phases of the game.
 */
public abstract class Phase {
    /**
     * Constructor
     */
    public Phase() {}
    /**
     * Loads a map into the game.
     *
     * @param p_mapDto The map DTO containing map data.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String loadMap(MapDto p_mapDto);

    /**
     * Moves the game to the next phase.
     *
     * @return The next phase of the game.
     */
    abstract public Phase next();

    /**
     * Edits a map.
     *
     * @param p_mapDto The map DTO containing map data to be edited.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String editMap(MapDto p_mapDto);

    /**
     * Displays the current map.
     *
     * @return A string representation of the current map.
     */
    abstract public String showMap();

    /**
     * Saves the current map state.
     *
     * @param p_dto The map DTO containing the map data to be saved.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String saveMap(MapDto p_dto);

    /**
     * Adds a continent to the map.
     *
     * @param p_continentDto The continent DTO containing continent data.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String addContinent(ContinentDto p_continentDto);

    /**
     * Removes a continent from the map.
     *
     * @param p_continentId The ID of the continent to be removed.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String removeContinent(String p_continentId);

    /**
     * Adds a country to the map.
     *
     * @param p_dto The country DTO containing country data.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String addCountry(CountryDto p_dto);

    /**
     * Removes a country from the map.
     *
     * @param p_countryId The ID of the country to be removed.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String removeCountry(String p_countryId);

    /**
     * Adds a neighbor to a country on the map.
     *
     * @param p_dto The country DTO containing neighbor data.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String addNeighbor(CountryDto p_dto);

    /**
     * Removes a neighbor from a country on the map.
     *
     * @param p_dto The country DTO containing neighbor data.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String removeNeighbor(CountryDto p_dto);

    /**
     * Adds a player to the game.
     *
     * @param p_playerDto The player DTO containing player data.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String addPlayer(PlayerDto p_playerDto);

    /**
     * Removes a player from the game.
     *
     * @param p_playerName The name of the player to be removed.
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String removePlayer(String p_playerName);

    /**
     * Assigns countries to players at the start of the game.
     *
     * @return A message indicating the success or failure of the operation.
     */
    abstract public String assignCountries();

    /**
     * Adds deploy orders to a player during the game.
     *
     * @param countryId The ID of the country to deploy armies to.
     * @param numOfReinforcements The number of armies to deploy.
     * @param p_currentPlayerGivingOrder The ID of the current player issuing the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    public abstract String addDeployOrdersToPlayer(String countryId, int numOfReinforcements, int p_currentPlayerGivingOrder, int p_currentRound);

    /**
     * Adds advance orders to a player during the game.
     *
     * @param countryNameFrom The name of the country to advance armies from.
     * @param countryNameTo The name of the country to advance armies to.
     * @param armiesQuantity The number of armies to advance.
     * @param p_currentPlayerGivingOrder The ID of the current player issuing the order.
     * @param p_currentRound The current round number.
     * @param p_diplomacyList The list of diplomacy contracts for the current round
     * @return A message indicating the success or failure of the operation.
     */
    public abstract String addAdvanceOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound, List<List<String>> p_diplomacyList);

    /**
     * Adds airlift orders to a player during the game.
     *
     * @param countryNameFrom The name of the country to airlift armies from.
     * @param countryNameTo The name of the country to airlift armies to.
     * @param armiesQuantity The number of armies to airlift.
     * @param p_currentPlayerGivingOrder The ID of the current player issuing the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    public abstract String addAirliftOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound);

    /**
     * Adds blockade orders to a player during the game.
     *
     * @param country The name of the country to blockade.
     * @param p_currentPlayerGivingOrder The ID of the current player issuing the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    public abstract String addBlockadeOrderToPlayer(String country, int p_currentPlayerGivingOrder, int p_currentRound);
}
