package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class DefaultMapFormatterTest {

    private final DefaultMapFileFormatter underTest = new DefaultMapFileFormatter();

    @Test
    public void testStringToMap() {

        List<String> lines = List.of(
                "[continents]",
                "1 10",
                "2 15",
                "",
                "[countries]",
                "Canada 1",
                "USA 1",
                "Spain 2",
                "France 2",
                "",
                "[borders]",
                "Canada USA",
                "USA Canada",
                "USA France",
                "France Spain",
                "Spain USA"
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

    private void assertThatCountryNeighboursAre(Country country, String... neighbors) {
        assertThat(country.getNeighbors(), hasSize(neighbors.length));

        for (int i = 0; i < neighbors.length; i++) {
            assertThat(country.getNeighbors().get(i).getId(), is(neighbors[i]));
        }
    }
}
