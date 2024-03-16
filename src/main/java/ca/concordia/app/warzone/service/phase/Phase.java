package ca.concordia.app.warzone.service.phase;


import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.service.ContinentService;

/**
 * Represents the phases of the game.
 */
public abstract class Phase {
    abstract public String loadMap(MapDto p_mapDto);
    abstract public Phase next();
    abstract public String editMap(MapDto p_mapDto);
    abstract public String showMap();
    abstract public String saveMap(MapDto p_dto);
    abstract public String addContinent(ContinentDto p_continentDto);
    abstract public String removeContinent(String p_continentId);
    abstract public String addCountry(CountryDto p_dto);
    abstract public String removeCountry(String p_countryId);
    abstract public String addNeighbor(CountryDto p_dto);

    abstract public String removeNeighbor(CountryDto p_dto);
}



