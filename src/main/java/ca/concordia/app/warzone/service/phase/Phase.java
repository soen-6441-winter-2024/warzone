package ca.concordia.app.warzone.service.phase;


import ca.concordia.app.warzone.console.dto.MapDto;

/**
 * Represents the phases of the game.
 */
public abstract class Phase {
    abstract public String loadMap(MapDto mapDto);
    abstract public Phase next();
}


