package ca.concordia.app.warzone.service.phase;


import java.util.List;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.ContinentService;

/**
 * Represents the phases of the game.
 */
public abstract class Phase {
    // GameEditor phases
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

    // Startup phase
    abstract public String addPlayer(PlayerDto p_playerDto);
    abstract public String removePlayer(String p_playerName);
    abstract public String assignCountries();

    // Game play phase
    public abstract String addDeployOrdersToPlayer(String countryId, int numOfReinforcements, int p_currentPlayerGivingOrder, int p_currentRound);

    public abstract String addAdvanceOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound, List<List<String>> p_diplomacyList);

    public abstract String addAirliftOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound);
    public abstract String addBlockadeOrderToPlayer(String country, int p_currentPlayerGivingOrder, int p_currentRound);
}



