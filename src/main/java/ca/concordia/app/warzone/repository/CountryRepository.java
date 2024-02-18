package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.service.model.Country;

import java.util.Optional;
import java.util.List;

public interface CountryRepository {
    void save(Country domain);
    Optional<Country> findById(String id);
    void deleteById(String id);
    void deleteNeighborById(Country domain);
    List<Country> findAll();
}
