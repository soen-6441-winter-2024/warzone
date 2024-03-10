package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

public class ContinentRepositoryMemoryImplTest {

    public static final String CONTINENT_ID = "AMERICA";
    public static final String CONTINENT_ID_2 = "EUROPE";
    private ContinentRepositoryMemoryImpl underTest;

    @BeforeEach
    public void before() {
        underTest = new ContinentRepositoryMemoryImpl();
    }

    @Test
    public void testSave() {
        Continent entity =  newContinent(CONTINENT_ID);

        underTest.save(entity);

        Optional<Continent> result = underTest.findById(CONTINENT_ID);
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    public void testFindById() {
        Continent entity =  newContinent(CONTINENT_ID);
        Continent entity2 =  newContinent(CONTINENT_ID_2);
        underTest.save(entity);
        underTest.save(entity2);

        assertTrue(underTest.findById(CONTINENT_ID).isPresent());
        assertEquals(underTest.findById(CONTINENT_ID).get(), entity);
        assertTrue(underTest.findById(CONTINENT_ID_2).isPresent());
        assertEquals(underTest.findById(CONTINENT_ID_2).get(), entity2);
    }

    @Test
    public void testFindAll() {
        Continent entity =  newContinent(CONTINENT_ID);
        Continent entity2 =  newContinent(CONTINENT_ID_2);
        underTest.save(entity);
        underTest.save(entity2);

        List<Continent> result = underTest.findAll();

        assertEquals(2, result.size());
        assertThat(result, hasItem(entity));
        assertThat(result, hasItem(entity2));
    }

    @Test
    public void testDeleteById() {
        Continent entity =  newContinent(CONTINENT_ID);
        underTest.save(entity);

        underTest.deleteById(CONTINENT_ID);

        assertFalse(underTest.findById(CONTINENT_ID).isPresent());
    }

    private Continent newContinent(String id) {
        Continent entity = new Continent();
        entity.setId(id);
        entity.setValue("10");

        return entity;
    }
}
