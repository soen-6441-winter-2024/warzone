package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.service.ContinentService;

public class GamePhase extends Phase {
    @Override
    public String loadMap(MapDto mapDto) {
        return null;
    }

    @Override
    public Phase next() {
        return null;
    }

    @Override
    public String editMap(MapDto p_mapDto) {
        return null;
    }

    @Override
    public String showMap() {
        return "Implement me";
    }

    @Override
    public String saveMap(MapDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String addContinent(ContinentDto p_continentDto) {
        return  "Invalid phase";
    }

    @Override
    public String removeContinent(String p_continentId) {
        return "Invalid phase";
    }

    @Override
    public String addCountry(CountryDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String removeCountry(String p_countryId) {
        return "Invalid phase";
    }

    @Override
    public String addNeighbor(CountryDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String removeNeighbor(CountryDto p_dto) {
        return "Invalid phase";
    }
}
