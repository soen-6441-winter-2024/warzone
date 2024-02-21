package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.model.Continent;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Implementation of the ContinentRepository interface using an in-memory data structure.
 */
@Repository
public class ContinentRepositoryMemoryImpl implements ContinentRepository {

    private Map<String, Continent> d_continents = new HashMap<>(); // Stores continents with their IDs as keys

    /**
     * Default constructor
     */
    public ContinentRepositoryMemoryImpl() {}

    /**
     * Saves a continent to the repository.
     *
     * @param p_continent the continent to be saved
     */
    @Override
    public void save(Continent p_continent) {
        d_continents.put(p_continent.getId(), p_continent);
    }

    /**
     * Retrieves a continent by its ID.
     *
     * @param p_id the ID of the continent to retrieve
     * @return an Optional containing the continent, or empty if not found
     */
    @Override
    public Optional<Continent> findById(String p_id) {
        return Optional.ofNullable(d_continents.get(p_id));
    }

    /**
     * Deletes a continent by its ID.
     *
     * @param id the ID of the continent to delete
     */
    @Override
    public void deleteById(String id) {
        d_continents.remove(id);
    }

    /**
     * Retrieves all continents stored in the repository.
     *
     * @return a list of all continents
     */
    @Override
    public List<Continent> findAll() {
        return new ArrayList<>(d_continents.values());
    }
}
