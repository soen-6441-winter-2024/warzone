package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;

public class MapEditorPhase extends Phase {
    private final MapService d_mapService;
    private final ContinentService d_continentService;
    private final CountryService d_countryService;

    public  MapEditorPhase(MapService p_mapService, ContinentService p_continentService, CountryService p_countryService) {
        this.d_mapService = p_mapService;
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
    }

    @Override
    public String loadMap(MapDto mapDto) {
        return d_mapService.loadMap(mapDto);
    }

    @Override
    public Phase next() {
        return new GameStartupPhase();
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
}
