package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.model.Continent;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Implementation of the ContinentRepository interface using an in-memory data structure.
 */
@Repository
public class ContinentRepositoryMemoryImpl extends AbstractRepositoryMemoryImpl<Continent> implements ContinentRepository {

    private Map<String, Continent> d_continents = new HashMap<>(); // Stores continents with their IDs as keys

    /**
     * Default constructor
     */
    public ContinentRepositoryMemoryImpl() {}

    @Override
    Map<String, Continent> getMap() {
        return d_continents;
    }
}
