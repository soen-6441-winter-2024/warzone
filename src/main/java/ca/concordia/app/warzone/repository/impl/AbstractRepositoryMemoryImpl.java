package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Entity;
import ca.concordia.app.warzone.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract implementation od a memory based repository, it implements basic operations such as save, findById,
 * delete and findAll
 *
 * @param <T> Entity to be persisted, must implement @see ca.concordia.app.warzone.model.Entity interface
 */
public abstract class AbstractRepositoryMemoryImpl<T extends Entity> implements AbstractRepository<T> {
    /**
     * Constructor
     */
    public AbstractRepositoryMemoryImpl() {}
    @Override
    public void save(T p_entity) {
        getMap().put(p_entity.getId(), p_entity);
    }

    @Override
    public Optional<T> findById(String p_id) {
        return Optional.ofNullable(getMap().get(p_id));
    }

    @Override
    public void deleteById(String p_id) {
        getMap().remove(p_id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(getMap().values());
    }

    @Override
    public void deleteAll() {
        getMap().clear();
    }

    /**
     * Abstract method that must return the map representation of the repository
     *
     * @return
     */
    abstract Map<String, T> getMap();
}
