package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.PlayerService;
import org.springframework.stereotype.Component;

@Component
public class GameEngineController {

    private final ContinentService continentService;
    private final CountryService countryService;

    private final PlayerService playerService;

    private Phase current = Phase.MAP_EDITOR;

    public GameEngineController(ContinentService continentService, CountryService countryService, PlayerService playerService) {
        this.continentService = continentService;
        this.countryService = countryService;
        this.playerService = playerService;
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
        return "Country " + dto.getId() + " added";
    }

    public String addPlayer(PlayerDto playerDto){
        String playerName = playerDto.get_playerName();
        String response = playerService.add(playerDto);
        return response;
    }

    public  String removePlayer(String playerName){
        return playerService.remove(playerName);
    }
}
