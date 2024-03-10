package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerRepositoryMemoryImplTest {

    public static final String PLAYER_ID = "JORGE";
    public static final String PLAYER_ID_2 = "LUIS";
    private PlayerRepositoryMemoryImpl underTest;

    @BeforeEach
    public void before() {
        underTest = new PlayerRepositoryMemoryImpl();
    }

    @Test
    public void testSave() {
        Player entity =  newPlayer(PLAYER_ID);

        underTest.save(entity);

        Optional<Player> result = underTest.findById("JORGE");
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    public void testFindById() {
        Player entity =  newPlayer(PLAYER_ID);
        Player entity2 =  newPlayer(PLAYER_ID_2);
        underTest.save(entity);
        underTest.save(entity2);

        assertTrue(underTest.findById(PLAYER_ID).isPresent());
        assertEquals(underTest.findById(PLAYER_ID).get(), entity);
        assertTrue(underTest.findById(PLAYER_ID_2).isPresent());
        assertEquals(underTest.findById(PLAYER_ID_2).get(), entity2);
    }

    @Test
    public void testFindAll() {
        Player entity =  newPlayer(PLAYER_ID);
        Player entity2 =  newPlayer(PLAYER_ID_2);
        underTest.save(entity);
        underTest.save(entity2);

        List<Player> result = underTest.findAll();

        assertEquals(2, result.size());
        assertThat(result, hasItem(entity));
        assertThat(result, hasItem(entity2));
    }

    @Test
    public void testDeleteById() {
        Player entity =  newPlayer(PLAYER_ID);
        underTest.save(entity);

        underTest.deleteById(entity.getId());

        assertFalse(underTest.findById(PLAYER_ID).isPresent());
    }

    private Player newPlayer(String name) {
        Player entity = new Player();
        entity.setId(name);
        entity.setNumberOfReinforcements(10);

        return entity;
    }
}
