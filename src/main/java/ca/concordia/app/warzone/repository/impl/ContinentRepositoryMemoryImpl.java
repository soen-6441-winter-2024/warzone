package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.service.model.Continent;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ContinentRepositoryMemoryImpl implements ContinentRepository {

    private Map<String, Continent> continents  = new HashMap<>();

    @Override
    public void save(Continent continent) {
        continents.put(continent.getId(), continent);
    }
    @Override
    public Optional<Continent> findById(String id) {
        return Optional.ofNullable(continents.get(id));
    }
    @Override
    public void deleteById(String id) {
        continents.remove(id);
    }
}
