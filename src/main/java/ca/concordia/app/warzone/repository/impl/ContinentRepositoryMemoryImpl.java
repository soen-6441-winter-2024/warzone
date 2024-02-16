package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.service.model.Continent;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ContinentRepositoryMemoryImpl implements ContinentRepository {

    private Map<String, Continent> values = new HashMap<>();

    @Override
    public void save(Continent domain) {
        values.put(domain.getId(), domain);
    }

    @Override
    public Optional<Continent> findById(String id) {
        return Optional.ofNullable(values.get(id));
    }
}
