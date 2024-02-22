package ca.concordia.app.warzone.repository;
import ca.concordia.app.warzone.model.Continent;

import java.util.List;
import java.util.Optional;

/**
 * Interface for accessing and managing continents.
 */
public interface ContinentRepository {

    /**
     * Saves a continent.
     *
     * @param p_continent the continent to save
     */
    void save(Continent p_continent);

    /**
     * Finds a continent by its ID.
     *
     * @param p_id the ID of the continent to find
     * @return an Optional containing the continent, or empty if not found
     */
    Optional<Continent> findById(String p_id);

    /**
     * Deletes a continent by its ID.
     *
     * @param p_id the ID of the continent to delete
     */
    void deleteById(String p_id);

    /**
     * Retrieves all continents.
     *
     * @return a list of all continents
     */
    List<Continent> findAll();
}
