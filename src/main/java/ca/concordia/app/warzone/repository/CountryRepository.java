package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.service.model.Country;

import java.util.Optional;

public interface CountryRepository {
    void save(Country domain);

    Optional<Country> findById(String id);
}
