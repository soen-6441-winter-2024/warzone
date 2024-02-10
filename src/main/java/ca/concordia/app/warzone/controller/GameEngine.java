package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.service.MapService;

public class GameEngine {

    private MapService mapService = new MapService();

    private Phase current;

    public String addContinent(ContinentDto continentDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return mapService.addContinent(continentDto);
        } else {
            return "Invalid Phase";
        }
    }

    public String deleteContinent(String continentId) {
        return null;
    }
}
