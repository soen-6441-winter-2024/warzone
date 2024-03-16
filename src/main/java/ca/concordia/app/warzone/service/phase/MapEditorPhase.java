package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.service.MapService;

public class MapEditorPhase extends Phase {
    private final MapService d_mapService;

    public  MapEditorPhase(MapService p_mapService) {
        this.d_mapService = p_mapService;
    }

    public String loadMap(MapDto mapDto) {
        return d_mapService.loadMap(mapDto);
    }

    public Phase next() {
        return new GameStartupPhase();
    }
}
