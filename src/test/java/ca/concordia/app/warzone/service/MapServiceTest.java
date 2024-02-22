package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MapServiceTest {

    public static final String FILE_NAME = "src/test/resources/map.txt";
    public static final String VALID_FILE_NAME = "src/test/resources/valid_map.txt";
    public static final String INVALID_FILE_NAME = "src/test/resources/invalid_map.txt";
    public static final String EXPECTED_FILE_NAME = "src/test/resources/expected_map.txt";
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ContinentRepository continentRepository;

    @Mock
    private PlayerService playerService;
    private MapService underTest;

    @BeforeEach
    public void before() {
        underTest = new MapService(countryRepository, continentRepository);
        FileUtils.deleteQuietly(new File(FILE_NAME));
    }

    @Test
    public void testSaveMap() throws IOException {

        //Arrange
        Continent l_america = newContinent("1", "America");
        Continent l_europe = newContinent("2", "Europe");
        Continent l_africa = newContinent("3", "Africa");
        List<Continent> continentList = List.of(
                l_america,
                l_europe,
                l_africa
        );

        Country l_canada = newCountry("Canada", l_america);
        Country l_usa = newCountry("USA", l_america);
        Country l_nigeria = newCountry("Nigeria", l_africa);
        Country l_france = newCountry("France", l_europe);

        l_canada.addNeighbor(l_usa);
        l_usa.addNeighbor(l_canada);
        l_usa.addNeighbor(l_nigeria);
        l_france.addNeighbor(l_nigeria);
        l_nigeria.addNeighbor(l_usa);

        List<Country> countryList = List.of(
                l_canada,
                l_usa,
                l_nigeria,
                l_france);

        when(continentRepository.findAll()).thenReturn(continentList);
        when(countryRepository.findAll()).thenReturn(countryList);

        MapDto dto = new MapDto();
        dto.setFileName(FILE_NAME);

        //Act
        String result = underTest.saveMap(dto);

        //Assert
        assertEquals("Map was saved in the following filepath " + FILE_NAME,result);
        assertEquals(readFileToString(new File(EXPECTED_FILE_NAME), StandardCharsets.UTF_8),
                readFileToString(new File(FILE_NAME), StandardCharsets.UTF_8));
    }

    @Test
    public void testValidateMapStructure() {

        boolean result = underTest.validateMapStructure(VALID_FILE_NAME);

        assertTrue(result);
    }

    @Test
    public void testValidateMapStructure_Invalid() {

        boolean result = underTest.validateMapStructure(INVALID_FILE_NAME);

        assertFalse(result);
    }

    private Country newCountry(String id, Continent continent) {
        Country l_object = new Country();
        l_object.setId(id);
        l_object.setContinent(continent);

        return l_object;
    }

    private Continent newContinent(String id, String value) {
        Continent l_object = new Continent();
        l_object.setId(id);
        l_object.setValue(value);
        return l_object;
    }
}
