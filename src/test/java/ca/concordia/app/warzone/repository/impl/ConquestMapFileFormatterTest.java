package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConquestMapFileFormatterTest {

    private final ConquestMapFileFormatter underTest = new ConquestMapFileFormatter();

    @Test
    public void testStringToMap() {

        List<String> lines = List.of(
                "[Continents]",
                "1=10",
                "2=15",
                "",
                "[Territories]",
                "Canada,,,1,USA",
                "USA,,,1,Canada,France",
                "Spain,,,2,USA",
                "France,,,2,Spain"
        );

        MapFile map = underTest.stringToMap(lines);

        assertThat(map.getContinents(), hasSize(2));
        assertThat(map.getCountries(), hasSize(4));

        for (Country country : map.getCountries()) {

            switch (country.getId()) {
                case "Canada", "Spain": {
                    assertThatCountryNeighboursAre(country, "USA");
                    break;
                }

                case "USA": {
                    assertThatCountryNeighboursAre(country, "Canada", "France");
                    assertThat(country.getNeighbors(), hasSize(2));
                    break;
                }

                case "France": {
                    assertThatCountryNeighboursAre(country, "Spain");
                    break;
                }
            }
        }
    }

    @Test
    public void testMapToString() {

        MapFile map = new MapFile();
        List<Continent> continents = List.of(
                newContinent("1", "10"),
                newContinent("2", "20")
        );

        map.setContinents(continents);

        List<Country> countries = List.of(
                newCountry("Canada", "1", "USA"),
                newCountry("USA", "1", "Canada", "France"),
                newCountry("Spain", "2", "USA"),
                newCountry("France", "2", "Spain")
        );

        map.setCountries(countries);

        String content = underTest.mapToString(map);

        String expected = """
                [Continents]
                1=10
                2=20
                                
                [Territories]
                Canada,,,1,USA
                USA,,,1,Canada,France
                Spain,,,2,USA
                France,,,2,Spain
                """;

        assertEquals(expected, content);
    }

    private void assertThatCountryNeighboursAre(Country country, String... neighbors) {
        assertThat(country.getNeighbors(), hasSize(neighbors.length));

        for (int i = 0; i < neighbors.length; i++) {
            assertThat(country.getNeighbors().get(i).getId(), is(neighbors[i]));
        }
    }

    private Continent newContinent(String id, String value) {
        Continent object = new Continent();
        object.setId(id);
        object.setValue(value);
        return object;
    }

    private Country newCountry(String id, String continentId, String... neighbours) {
        Country object = new Country();
        object.setId(id);

        if (continentId != null) {
            object.setContinent(newContinent(continentId, null));
        }

        if (neighbours != null ) {
            for (String neighbourId : neighbours) {
                object.addNeighbor(newCountry(neighbourId, null));
            }
        }
        return object;
    }
}
