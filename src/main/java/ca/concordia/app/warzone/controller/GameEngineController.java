package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import org.springframework.stereotype.Component;

@Component
public class GameEngineController {

    private ContinentService service;
    private CountryService countryService;

    public GameEngineController(ContinentService service, CountryService countryService) {
        this.service = service;
        this.countryService = countryService;
    }

    private MapService mapService = new MapService();

    private Phase current = Phase.MAP_EDITOR;

    public String addContinent(ContinentDto continentDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return service.add(continentDto);
        } else {
            return "Invalid Phase";
        }
    }

    public String deleteContinent(String continentId) {
        return null;
    }

    public void removeCountry(String option) {

    }

    public void addCountry(CountryDto dto) {
        countryService.add(dto);
    }
}
