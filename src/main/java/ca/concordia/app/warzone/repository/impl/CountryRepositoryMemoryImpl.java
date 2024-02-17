package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.service.model.Continent;
import ca.concordia.app.warzone.service.model.Country;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CountryRepositoryMemoryImpl implements CountryRepository {

    private Map<String, Country> countries = new HashMap<>();

    @Override
    public void save(Country country) {
        countries.put(country.getId(), country);
    }
    @Override
    public Optional<Country> findById(String id) {
        return Optional.ofNullable(countries.get(id));
    }
    @Override
    public void deleteById(String id) {
        countries.remove(id);
    }
    @Override
    public void deleteNeighborById(Country country) {
        countries.put(country.getId(), country);
    }
}
