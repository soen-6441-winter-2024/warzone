package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.service.model.Continent;

import java.util.Optional;

public interface ContinentRepository {
    void save(Continent continent);

    Optional<Continent> findById(String id);

    void deleteById(String id);
}
