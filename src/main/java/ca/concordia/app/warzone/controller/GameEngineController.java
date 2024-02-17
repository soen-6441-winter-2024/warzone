package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import org.springframework.stereotype.Component;

@Component
public class GameEngineController {

    private final ContinentService continentService;
    private final CountryService countryService;

    private Phase current = Phase.MAP_EDITOR;

    public GameEngineController(ContinentService continentService, CountryService countryService) {
        this.continentService = continentService;
        this.countryService = countryService;
    }

    public String addContinent(ContinentDto continentDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return continentService.add(continentDto);
        } else {
            return "Invalid Phase";
        }
    }

    public String deleteContinent(String continentId) {
        return null;
    }

    public String removeCountry(String option) {
        return null;
    }

    public String addCountry(CountryDto dto) {
        countryService.add(dto);
        return "Country " + dto.id() + " added";
    }
}
