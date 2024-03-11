package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.repository.CountryRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the CountryRepository interface using an in-memory data structure.
 */
@Repository
public class CountryRepositoryMemoryImpl extends AbstractRepositoryMemoryImpl<Country> implements CountryRepository{

    /**
     * Default constructor
     */
    public CountryRepositoryMemoryImpl() {}
    private Map<String, Country> d_countries = new HashMap<>(); // Stores countries with their IDs as keys

    /**
     * Returns all countries that match the continent id
     * @param p_continentId continentId
     * @return a list of countries
     */
    public List<Country> findByContinentId(String p_continentId) {
            return this.d_countries.values().stream().filter(c -> c.getContinent().getId().equals(p_continentId)).collect(Collectors.toList());
    }

    /**
     * Deletes a neighboring country by its ID.
     *
     * @param p_country the country whose neighbor to delete
     */
    @Override
    public void deleteNeighborById(Country p_country) {
        d_countries.put(p_country.getId(), p_country);
    }

    @Override
    Map<String, Country> getMap() {
        return d_countries;
    }
}
