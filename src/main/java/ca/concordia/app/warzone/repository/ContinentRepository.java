package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.service.model.Continent;

import java.util.Optional;

public interface ContinentRepository {
    void save(Continent continentDto);

    Optional<Continent> findById(String id);
}
