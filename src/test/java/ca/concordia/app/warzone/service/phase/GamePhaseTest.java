package ca.concordia.app.warzone.service.phase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class GamePhaseTest {

    private GamePhase gamePhase;

    @BeforeEach
    public void setUp() {
        gamePhase = new GamePhase();
    }

    @Test
    public void testLoadMap() {
        MapDto mapDto = new MapDto();
        String result = gamePhase.loadMap(mapDto);
        assertEquals(null, result);
    }

    @Test
    public void testNext() {
        Phase result = gamePhase.next();
        assertEquals(null, result);
    }

    @Test
    public void testEditMap() {
        MapDto mapDto = new MapDto();
        String result = gamePhase.editMap(mapDto);
        assertEquals(null, result);
    }

    @Test
    public void testShowMap() {
        String result = gamePhase.showMap();
        assertEquals("Implement me", result);
    }

    @Test
    public void testSaveMap() {
        MapDto mapDto = new MapDto();
        String result = gamePhase.saveMap(mapDto);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddContinent() {
        ContinentDto continentDto = new ContinentDto();
        String result = gamePhase.addContinent(continentDto);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testRemoveContinent() {
        String result = gamePhase.removeContinent("continentId");
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddCountry() {
        CountryDto countryDto = new CountryDto();
        String result = gamePhase.addCountry(countryDto);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testRemoveCountry() {
        String result = gamePhase.removeCountry("countryId");
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddNeighbor() {
        CountryDto countryDto = new CountryDto();
        String result = gamePhase.addNeighbor(countryDto);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testRemoveNeighbor() {
        CountryDto countryDto = new CountryDto();
        String result = gamePhase.removeNeighbor(countryDto);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddPlayer() {
        PlayerDto playerDto = new PlayerDto();
        String result = gamePhase.addPlayer(playerDto);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testRemovePlayer() {
        String result = gamePhase.removePlayer("playerName");
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAssignCountries() {
        String result = gamePhase.assignCountries();
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddDeployOrdersToPlayer() {
        String result = gamePhase.addDeployOrdersToPlayer("countryId", 5, 1, 1);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddAdvanceOrderToPlayer() {
        List<List<String>> diplomacyList = new ArrayList<>();
        String result = gamePhase.addAdvanceOrderToPlayer("countryNameFrom", "countryNameTo", 5, 1, 1, diplomacyList);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddAirliftOrderToPlayer() {
        String result = gamePhase.addAirliftOrderToPlayer("countryNameFrom", "countryNameTo", 5, 1, 1);
        assertEquals("Invalid phase", result);
    }

    @Test
    public void testAddBlockadeOrderToPlayer() {
        String result = gamePhase.addBlockadeOrderToPlayer("country", 1, 1);
        assertEquals("Invalid phase", result);
    }
}
