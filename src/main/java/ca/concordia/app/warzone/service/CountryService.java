package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.service.model.Country;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository repository;

    private final ContinentService continentService;

    public CountryService(CountryRepository repository, ContinentService continentService) {
        this.repository = repository;
        this.continentService = continentService;
    }

    public String add(CountryDto dto) {

        if (continentService.findById(dto.continentId()).isPresent()) {

            Country domain = new Country();
            domain.setId(dto.id());

            repository.save(domain);
            return "OK";
        } else {
            return "Continent doesn't exist";
        }
    }

    public Optional<Country> findById(String id) {
        return repository.findById(id);
    }
}
