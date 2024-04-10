package ca.concordia.app.warzone.service.phase;

import java.util.List;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;

/**
 * Represents a generic game phase.
 */
public class GamePhase extends Phase {

    /**
     * Constructor
     */
    public GamePhase(){}
    @Override
    public String loadMap(MapDto mapDto) {
        return null;
    }

    @Override
    public Phase next() {
        return null;
    }

    @Override
    public String editMap(MapDto p_mapDto) {
        return null;
    }

    @Override
    public String showMap() {
        return "Implement me";
    }

    @Override
    public String saveMap(MapDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String addContinent(ContinentDto p_continentDto) {
        return  "Invalid phase";
    }

    @Override
    public String removeContinent(String p_continentId) {
        return "Invalid phase";
    }

    @Override
    public String addCountry(CountryDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String removeCountry(String p_countryId) {
        return "Invalid phase";
    }

    @Override
    public String addNeighbor(CountryDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String removeNeighbor(CountryDto p_dto) {
        return "Invalid phase";
    }

    @Override
    public String addPlayer(PlayerDto p_playerDto) {
        return "Invalid phase";
    }

    @Override
    public String removePlayer(String p_playerName) {
        return "Invalid phase";
    }

    @Override
    public String assignCountries() {
        return "Invalid phase";
    }

    @Override
    public String addDeployOrdersToPlayer(String countryId, int numOfReinforcements, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }

    @Override
    public String addAdvanceOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound, List<List<String>> p_diplomacyList) {
        return "Invalid phase";
    }

    @Override
    public String addAirliftOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }

    @Override
    public String addBlockadeOrderToPlayer(String country, int p_currentPlayerGivingOrder, int p_currentRound) {
        return "Invalid phase";
    }
}
