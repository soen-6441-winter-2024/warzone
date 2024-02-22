package ca.concordia.app.warzone.repository;
import ca.concordia.app.warzone.model.Country;

import java.util.Optional;
import java.util.List;

/**
 * Interface for accessing and managing countries.
 */
public interface CountryRepository {

    /**
     * Saves a country.
     *
     * @param p_domain the country to save
     */
    void save(Country p_domain);

    /**
     * Finds a country by its ID.
     *
     * @param p_id the ID of the country to find
     * @return an Optional containing the country, or empty if not found
     */
    Optional<Country> findById(String p_id);

    /**
     * Deletes a country by its ID.
     *
     * @param p_id the ID of the country to delete
     */
    void deleteById(String p_id);

    /**
     * Deletes a neighbor of a country.
     *
     * @param p_id the country whose neighbor to delete
     */
    void deleteNeighborById(Country p_id);

    /**
     * Retrieves all countries.
     *
     * @return a list of all countries
     */
    List<Country> findAll();

    /**
     * Returns all countries that match the continent id
     * @param p_continentId continentId
     * @return a list of countries
     */
    List<Country> findByContinentId(String p_continentId);
}
