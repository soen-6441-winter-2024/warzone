package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.model.Country;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CountryRepositoryMemoryImpl implements CountryRepository {

    private Map<String, Country> values = new HashMap<>();

    @Override
    public void save(Country domain) {
        values.put(domain.getId(), domain);
    }

    @Override
    public Optional<Country> findById(String id) {
        return Optional.ofNullable(values.get(id));
    }
}
