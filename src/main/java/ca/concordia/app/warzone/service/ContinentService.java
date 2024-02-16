package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.service.model.Continent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContinentService {

    private final ContinentRepository repository;

    public ContinentService(ContinentRepository repository) {
        this.repository = repository;
    }

    public String add(ContinentDto continentDto) {
        Continent domain = new Continent();
        domain.setId(continentDto.getId());
        domain.setValue(continentDto.getValue());
        repository.save(domain);
        return "OK";
    }

    public Optional<Continent> findById(String id) {
        return repository.findById(id);
    }
}
