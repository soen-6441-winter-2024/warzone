package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.model.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Generic Interface that defines common methods to interact with a repository
 *
 * @param <T> Entity that will be stored in the repository
 */
public interface AbstractRepository<T extends Entity> {

    /**
     * Saves an entity T to the repository.
     *
     * @param p_entity the entity to be saved
     */
    void save(T p_entity);

    /**
     * Retrieves an entity by its ID.
     *
     * @param p_id the ID of the entity to retrieve
     * @return an Optional containing the entity, or empty if not found
     */
    Optional<T> findById(String p_id);

    /**
     * Deletes an entity by its ID.
     *
     * @param p_id the ID of the entity to delete
     */
    void deleteById(String p_id);

    /**
     * Retrieves all entities stored in the repository.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Deletes all the data in the repository
     *
     */
    void deleteAll();
}
