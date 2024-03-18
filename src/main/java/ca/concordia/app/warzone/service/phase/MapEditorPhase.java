package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.PlayerService;

public class MapEditorPhase extends Phase {
    private final MapService d_mapService;
    private final ContinentService d_continentService;
    private final CountryService d_countryService;
    private final PlayerService d_playerService;

    public  MapEditorPhase(MapService p_mapService, ContinentService p_continentService, CountryService p_countryService, PlayerService p_playerService) {
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
