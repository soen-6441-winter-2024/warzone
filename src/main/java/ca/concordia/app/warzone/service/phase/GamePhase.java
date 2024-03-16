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

    public String editMap(MapDto p_mapDto) {
        return null;
    }

    public String showMap() {
        return "Implement me";
    }

    public String saveMap(MapDto p_dto) {
        return "Invalid phase";
    }

    public String addContinent(ContinentDto p_continentDto) {
        return  "Invalid phase";
    }

    @Override
    public String removeContinent(String p_continentId) {
        return "Invalid phase";
    }

    public String addCountry(CountryDto p_dto) {
        return "Invalid phase";
    }
}
