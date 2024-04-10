package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.ConsoleRunner;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MapServiceTest {

    public static final String FILE_NAME = "src/test/resources/map.txt";
    public static final String EXPECTED_SHOW_MAP_FILE_NAME = "src/test/resources/expectedShowMap.txt";
    public static final String EXPECTED_SAVE_MAP_FILE_NAME = "src/test/resources/expected_map.txt";
    public static final String VALID_FILE_NAME = "src/test/resources/valid_map.txt";
    public static final String VALID_FILE_NAME_NON_EXISTING = "src/test/resources/valid_map_2.txt";
    public static final String VALID_FILE_NAME_EMPTY_BORDERS = "src/test/resources/valid_map_empty_section.txt";
    public static final String INVALID_FILE_NAME_EMPTY_COUNTRIES = "src/test/resources/invalid_map_empty_countries.txt";
    public static final String INVALID_FILE_NAME = "src/test/resources/invalid_map.txt";
    public static final String INVALID_FILE_NAME_EMPTY_CONTINENTS = "src/test/resources/invalid_map_empty_continents.txt";

    @MockBean
    private ConsoleRunner consoleRunner;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ContinentRepository continentRepository;
    @Autowired
    private MapService underTest;

    @BeforeEach
    public void before() {
        continentRepository.deleteAll();
        countryRepository.deleteAll();
    }

    @Test
    public void testValidateMapStructure() {

        boolean result = underTest.validateMapStructure(VALID_FILE_NAME);

        assertTrue(result);
    }

    @Test
    public void testValidateEditMapStructure() {

        boolean result = underTest.validateEditMapStructure(VALID_FILE_NAME);

        assertTrue(result);
    }

    @Test
    public void testValidateEditMapStructure_NonExisting() {

        boolean result = underTest.validateEditMapStructure(VALID_FILE_NAME_NON_EXISTING);

        assertTrue(result);
    }

    @Test
    public void testValidateMapStructureEmptyBorders() {

        boolean result = underTest.validateMapStructure(VALID_FILE_NAME_EMPTY_BORDERS);

        assertFalse(result);
    }

    @Test
    public void testValidateMapStructureEmptyCountries() {

        boolean result = underTest.validateMapStructure(INVALID_FILE_NAME_EMPTY_COUNTRIES);

        assertFalse(result);
    }

    @Test
    public void testValidateMapStructure_Invalid() {

        boolean result = underTest.validateMapStructure(INVALID_FILE_NAME);

        assertFalse(result);
    }

    @Test
    public void testLoadMap() {
        MapDto mapDto = new MapDto();
        mapDto.setFileName(VALID_FILE_NAME);

        underTest.loadMap(mapDto);

        Optional<Continent> continentOpt = continentRepository.findById("1");
        assertTrue(continentOpt.isPresent());
        assertThat(continentOpt.get().getValue(), is("10"));

        continentOpt = continentRepository.findById("2");
        assertTrue(continentOpt.isPresent());
        assertThat(continentOpt.get().getValue(), is("15"));

        Optional<Country> countryOptional = countryRepository.findById("Canada");
        assertTrue(countryOptional.isPresent());
        assertThat(countryOptional.get().getContinent().getId(), is("1"));
        assertThat(countryOptional.get().getNeighbors(), hasSize(1));
        assertThat(countryOptional.get().getNeighbors().get(0).getId(), is("USA"));

        countryOptional = countryRepository.findById("USA");
        assertTrue(countryOptional.isPresent());
        assertThat(countryOptional.get().getContinent().getId(), is("1"));
        assertThat(countryOptional.get().getNeighbors(), hasSize(2));
        assertThat(countryOptional.get().getNeighbors().get(0).getId(), is("Canada"));
        assertThat(countryOptional.get().getNeighbors().get(1).getId(), is("France"));

        countryOptional = countryRepository.findById("Spain");
        assertTrue(countryOptional.isPresent());
        assertThat(countryOptional.get().getContinent().getId(), is("2"));
        assertThat(countryOptional.get().getNeighbors(), hasSize(1));
        assertThat(countryOptional.get().getNeighbors().get(0).getId(), is("USA"));

        countryOptional = countryRepository.findById("France");
        assertTrue(countryOptional.isPresent());
        assertThat(countryOptional.get().getContinent().getId(), is("2"));
        assertThat(countryOptional.get().getNeighbors(), hasSize(1));
        assertThat(countryOptional.get().getNeighbors().get(0).getId(), is("Spain"));

    }

    @Test
    public void testShowMap() throws IOException {
        String expected = readFileToString(new File(EXPECTED_SHOW_MAP_FILE_NAME));

        Continent continent = newContinent("Europe", "10");
        continentRepository.save(continent);

        Country country = newCountry("France", continent);
        countryRepository.save(country);

        Country country2 = newCountry("Spain", continent);
        country2.addNeighbor(country);
        countryRepository.save(country2);

        String result = underTest.showMap();

        assertThat(result, is(expected));
    }

    @Test
    public void testSaveMap() throws IOException {

        //Arrange
        Continent l_america = newContinent("1", "America");
        Continent l_europe = newContinent("2", "Europe");
        Continent l_africa = newContinent("3", "Africa");
        continentRepository.save(l_africa);
        continentRepository.save(l_america);
        continentRepository.save(l_europe);

        Country l_canada = newCountry("Canada", l_america);
        Country l_usa = newCountry("USA", l_america);
        Country l_nigeria = newCountry("Nigeria", l_africa);
        Country l_france = newCountry("France", l_europe);

        l_canada.addNeighbor(l_usa);
        l_usa.addNeighbor(l_canada);
        l_usa.addNeighbor(l_nigeria);
        l_france.addNeighbor(l_nigeria);
        l_nigeria.addNeighbor(l_usa);

        countryRepository.save(l_canada);
        countryRepository.save(l_usa);
        countryRepository.save(l_nigeria);
        countryRepository.save(l_france);

        MapDto dto = new MapDto();
        dto.setFileName(FILE_NAME);

        //Act
        String result = underTest.saveMap(dto);

        //Assert
        assertEquals("Map was saved in the following filepath " + FILE_NAME,result);
        assertEquals(readFileToString(new File(EXPECTED_SAVE_MAP_FILE_NAME), StandardCharsets.UTF_8),
                readFileToString(new File(FILE_NAME), StandardCharsets.UTF_8));

    }

    private Country newCountry(String id, Continent continent) {
        Country object = new Country();
        object.setId(id);
        object.setContinent(continent);
        return object;
    }

    private Continent newContinent(String id, String value) {
        Continent object = new Continent();
        object.setId(id);
        object.setValue(value);

        return object;
    }

    @Test
    public void testReadAndLoadMap() {
        boolean result = underTest.readAndLoadMap(VALID_FILE_NAME);

        assertTrue(result);
        assertThat(continentRepository.findAll(), hasSize(2));
        assertThat(countryRepository.findAll(), hasSize(4));
    }

    @Test
    public void testReadAndLoadMap_Invalid() {
        boolean result = underTest.readAndLoadMap(INVALID_FILE_NAME_EMPTY_CONTINENTS);

        assertFalse(result);
        assertTrue(continentRepository.findAll().isEmpty());
        assertTrue(countryRepository.findAll().isEmpty());
    }
}
