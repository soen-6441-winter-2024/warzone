package ca.concordia.app.warzone.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class PlayerTest {

    @Test
    public void testRemoveAssignedCountry() {

        Player player = new Player();
        List<Country> countries = new ArrayList<>();
        Country country1 = new Country();
        country1.setId("USA");
        countries.add(country1);
        Country country2 = new Country();
        country2.setId("CANADA");
        countries.add(country2);
        Country country3 = new Country();
        country3.setId("MEXICO");
        countries.add(country3);

        player.setCountriesAssigned(countries);

        player.removeAssignedCountry("CANADA");

        assertThat(player.getCountriesAssigned(), hasSize(2));
    }

    @Test
    public void testRemoveAssignedCountry_Duplicated() {

        Player player = new Player();
        List<Country> countries = new ArrayList<>();
        Country country1 = new Country();
        country1.setId("USA");
        countries.add(country1);
        Country country2 = new Country();
        country2.setId("CANADA");
        countries.add(country2);
        Country country3 = new Country();
        country3.setId("CANADA");
        countries.add(country3);
        Country country4 = new Country();
        country4.setId("MEXICO");
        countries.add(country4);

        player.setCountriesAssigned(countries);

        player.removeAssignedCountry("CANADA");

        assertThat(player.getCountriesAssigned(), hasSize(2));
    }
}
