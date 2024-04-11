package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MapRepositoryFileImplTest {

    public static final String FILE_NAME = "map_files/map.txt";
    public static final String EXPECTED_FILE_NAME = "src/test/resources/expected_map.txt";

    @Mock
    private CountryRepository countryRepository;
    @Mock
    private ContinentRepository continentRepository;

    private MapRepositoryFileImpl underTest;

    @BeforeEach
    public void before() {
        underTest = new MapRepositoryFileImpl(countryRepository, continentRepository, new DefaultMapFileFormatter());
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

        MapFile dto = new MapFile();
        dto.setFileName(FILE_NAME);

        //Act
        String result = underTest.saveMap(dto);

        //Assert
        assertEquals("Map was saved in the following filepath " + FILE_NAME,result);
        assertEquals(readFileToString(new File(EXPECTED_FILE_NAME), StandardCharsets.UTF_8),
                readFileToString(new File(FILE_NAME), StandardCharsets.UTF_8));
    }

    @Test
    public void testGetMapAsString() {

        List<String> lines = underTest.getMapAsString(EXPECTED_FILE_NAME);

        assertThat(lines, hasSize(16));
    }

    @Test
    public void testGetMap() {

        MapFile map = underTest.getMap(EXPECTED_FILE_NAME);

        assertThat(map, is(notNullValue()));
        assertThat(map.getContinents(), hasSize(3));
        assertThat(map.getCountries(), hasSize(4));
        assertThat(map.getCountries().get(0).getNeighbors(), hasSize(1));
        assertThat(map.getCountries().get(1).getNeighbors(), hasSize(2));
        assertThat(map.getCountries().get(2).getNeighbors(), hasSize(1));
        assertThat(map.getCountries().get(3).getNeighbors(), hasSize(1));
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
