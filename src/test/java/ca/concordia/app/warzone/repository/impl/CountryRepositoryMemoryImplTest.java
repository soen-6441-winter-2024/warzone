package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

public class CountryRepositoryMemoryImplTest {

    public static final String COUNTRY_ID = "1";
    public static final String COUNTRY_ID_2 = "2";
    public static final String CONTINENT_ID = "AMERICA";
    private CountryRepositoryMemoryImpl underTest;

    @BeforeEach
    public void before() {
        underTest = new CountryRepositoryMemoryImpl();
    }

    @Test
    public void testSave() {
        Country country =  newCountry(COUNTRY_ID);

        underTest.save(country);

        Optional<Country> result = underTest.findById(COUNTRY_ID);
        assertTrue(result.isPresent());
        assertEquals(country, result.get());
    }

    @Test
    public void testFindById() {
        Country country =  newCountry(COUNTRY_ID);
        Country country2 =  newCountry(COUNTRY_ID_2);
        underTest.save(country);
        underTest.save(country2);

        assertTrue(underTest.findById(COUNTRY_ID).isPresent());
        assertEquals(underTest.findById(COUNTRY_ID).get(), country);
        assertTrue(underTest.findById(COUNTRY_ID_2).isPresent());
        assertEquals(underTest.findById(COUNTRY_ID_2).get(), country2);
    }

    @Test
    public void testFindAll() {
        Country country =  newCountry(COUNTRY_ID);
        Country country2 =  newCountry(COUNTRY_ID_2);
        underTest.save(country);
        underTest.save(country2);

        List<Country> result = underTest.findAll();

        assertEquals(2, result.size());
        assertThat(result, hasItem(country));
        assertThat(result, hasItem(country2));
    }

    @Test
    public void testDeleteById() {
        Country country =  newCountry(COUNTRY_ID);
        underTest.save(country);

        underTest.deleteById(COUNTRY_ID);

        assertFalse(underTest.findById(COUNTRY_ID).isPresent());
    }

    private Country newCountry(String id) {
        Country country = new Country();
        country.setId(id);

        Continent continent = new Continent();
        continent.setId(CONTINENT_ID);
        continent.setValue("10");

        country.setContinent(continent);
        return country;
    }
}
