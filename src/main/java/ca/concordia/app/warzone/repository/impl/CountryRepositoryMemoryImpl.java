package ca.concordia.app.warzone.repository.impl;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.model.Country;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the CountryRepository interface using an in-memory data structure.
 */
@Repository
public class CountryRepositoryMemoryImpl implements CountryRepository {

    /**
     * Default constructor
     */
    public CountryRepositoryMemoryImpl() {}
    private Map<String, Country> d_countries = new HashMap<>(); // Stores countries with their IDs as keys

    /**
     * Saves a country to the repository.
     *
     * @param p_country the country to be saved
     */
    @Override
    public void save(Country p_country) {
        d_countries.put(p_country.getId(), p_country);
    }

    /**
     * Retrieves a country by its ID.
     *
     * @param p_id the ID of the country to retrieve
     * @return an Optional containing the country, or empty if not found
     */
    @Override
    public Optional<Country> findById(String p_id) {
        return Optional.ofNullable(d_countries.get(p_id));
    }

    /**
     * Returns all countries that match the continent id
     * @param p_continentId continentId
     * @return a list of countries
     */
    public List<Country> findByContinentId(String p_continentId) {
            return this.d_countries.values().stream().filter(c -> c.getContinent().getId().equals(p_continentId)).collect(Collectors.toList());
    }

    /**
     * Deletes a country by its ID.
     *
     * @param p_id the ID of the country to delete
     */
    @Override
    public void deleteById(String p_id) {
        d_countries.remove(p_id);
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

    /**
     * Retrieves all countries stored in the repository.
     *
     * @return a list of all countries
     */
    @Override
    public List<Country> findAll() {
        return new ArrayList<>(d_countries.values());
    }
}
