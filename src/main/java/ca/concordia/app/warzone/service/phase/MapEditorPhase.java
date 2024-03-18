package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.PlayerService;

/**
 * Represents the map editor phase of the game.
 */
public class MapEditorPhase extends Phase {
    /**
     * The map service for loading, editing, and saving maps.
     */
    private final MapService d_mapService;

    /**
     * The continent service for adding and removing continents.
     */
    private final ContinentService d_continentService;

    /**
     * The country service for adding, removing, and editing countries.
     */
    private final CountryService d_countryService;

    /**
     * The player service for adding and removing players.
     */
    private final PlayerService d_playerService;

    /**
     * Constructs a new map editor phase with the specified services.
     *
     * @param p_mapService The map service.
     * @param p_continentService The continent service.
     * @param p_countryService The country service.
     * @param p_playerService The player service.
     */
    public MapEditorPhase(MapService p_mapService, ContinentService p_continentService, CountryService p_countryService, PlayerService p_playerService) {
        this.d_mapService = p_mapService;
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
        this.d_playerService = p_playerService;
    }

    @Override
    public String loadMap(MapDto mapDto) {
        return d_mapService.loadMap(mapDto);
    }

    @Override
    public Phase next() {
        return new GameStartupPhase(d_playerService);
    }

    @Override
    public String editMap(MapDto p_mapDto) {
        return this.d_mapService.editMap(p_mapDto);
    }

    @Override
    public String showMap() {
        return d_mapService.showMap();
    }

    @Override
    public String saveMap(MapDto p_dto) {
        return this.d_mapService.saveMap(p_dto);
    }

    @Override
    public String addContinent(ContinentDto p_continentDto) {
        return  "Implement me";
    }

    @Override
    public String removeContinent(String p_continentId) {
        return this.d_continentService.delete(p_continentId);
    }

    @Override
    public String addCountry(CountryDto p_dto) {
        return this.d_countryService.add(p_dto);
    }

    @Override
    public String removeCountry(String p_countryId) {
        return this.d_countryService.delete(p_countryId);
    }

    @Override
    public String addNeighbor(CountryDto p_dto) {
        return this.d_countryService.addNeighbor(p_dto);
    }

    @Override
    public String removeNeighbor(CountryDto p_dto) {
        return this.d_countryService.deleteNeighbor(p_dto);
    }

    @Override
    public String addPlayer(PlayerDto p_playerDto) {
        return "Invalid phase";
    }

    @Override
    public String removePlayer(String p_playerName) {
        return "Invalid phase";
    }

    @Override
    public String assignCountries() {
        return "Invalid phase";
    }

    @Override
    public String addDeployOrdersToPlayer(String countryId, int numOfReinforcements, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }

    @Override
    public String addAdvanceOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }

    @Override
    public String addAirliftOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }

    @Override
    public String addBlockadeOrderToPlayer(String country, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }
}
