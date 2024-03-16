package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.service.ContinentService;

public class GameStartupPhase extends GamePhase {
    @Override
    public Phase next() {
        return null;
    }

    public String editMap(MapDto p_mapDto) {
        return "Invalid phase";
    }

}
