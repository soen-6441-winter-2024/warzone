package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.PlayerService;
import org.springframework.stereotype.Component;

/**
 * Controller class for managing game engine operations.
 */
@Component
public class GameEngineController {

    private final ContinentService d_continentService;
    private final CountryService d_countryService;
    private final PlayerService d_playerService;
    private final MapService d_mapService;

    private Phase d_current = Phase.MAP_EDITOR;

    /**
     * Constructs a GameEngineController with the specified services.
     *
     * @param p_continentService The ContinentService to use.
     * @param p_countryService   The CountryService to use.
     * @param p_playerService    The PlayerService to use.
     */
    public GameEngineController(ContinentService p_continentService, CountryService p_countryService,
                                PlayerService p_playerService, MapService p_mapService) {
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
        this.d_playerService = p_playerService;
        this.d_mapService = p_mapService;
    }

    /**
     * Adds a continent to the game.
     *
     * @param p_continentDto The ContinentDto representing the continent to add.
     * @return A string indicating the result of the operation.
     */
    public String addContinent(ContinentDto p_continentDto) {
        if (Phase.MAP_EDITOR.equals(d_current)) {
            return d_continentService.add(p_continentDto);
        } else {
            return "Invalid Phase";
        }
    }

    /**
     * Deletes a continent from the game.
     *
     * @param p_continentId The ID of the continent to delete.
     * @return A string indicating the result of the operation.
     */
    public String deleteContinent(String p_continentId) {
        // Implementation goes here
        return null;
    }

    /**
     * Removes a country from the game.
     *
     * @param p_option The option specifying the country to remove.
     * @return A string indicating the result of the operation.
     */
    public String removeCountry(String p_option) {
        // Implementation goes here
        return null;
    }

    /**
     * Adds a country to the game.
     *
     * @param p_dto The CountryDto representing the country to add.
     * @return A string indicating the result of the operation.
     */
    public String addCountry(CountryDto p_dto) {
        d_countryService.add(p_dto);
        return "Country " + p_dto.getId() + " added";
    }

    /**
     * Adds a player to the game.
     *
     * @param p_playerDto The PlayerDto representing the player to add.
     * @return A string indicating the result of the operation.
     */
    public String addPlayer(PlayerDto p_playerDto) {
        String playerName = p_playerDto.getPlayerName();
        return d_playerService.add(p_playerDto);
    }

    /**
     * Removes a player from the game.
     *
     * @param p_playerName The name of the player to remove.
     * @return A string indicating the result of the operation.
     */
    public String removePlayer(String p_playerName) {
        return d_playerService.remove(p_playerName);
    }

    /**
     * Loads a map into the game.
     *
     * @param p_filename The filename of the map to load.
     * @return A string indicating the result of the operation.
     */
    public String loadMap(String p_filename) {
        // Implementation goes here
        d_mapService.loadMap(p_filename);
        return "";
    }

    /**
     * Edits a map in the game.
     *
     * @param p_filename The filename of the map to edit.
     * @return A string indicating the result of the operation.
     */
    public String editMap(String p_filename) {
        // Implementation goes here
        return "";
    }
}
