package ca.concordia.app.warzone.repository;
import ca.concordia.app.warzone.model.Country;

import java.util.Optional;
import java.util.List;

/**
 * Interface for accessing and managing countries.
 */
public interface CountryRepository extends AbstractRepository<Country> {

    /**
     * Deletes a neighbor of a country.
     *
     * @param p_id the country whose neighbor to delete
     */
    void deleteNeighborById(Country p_id);

    /**
     * Returns all countries that match the continent id
     * @param p_continentId continentId
     * @return a list of countries
     */
    List<Country> findByContinentId(String p_continentId);
}
