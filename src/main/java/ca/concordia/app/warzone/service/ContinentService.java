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

        Optional<Continent> continentOptional = repository.findById(continentDto.getId());
        if (continentOptional.isPresent()) {
            return "Continent already exists";
        }
        else {
            Continent continent = new Continent();
            continent.setId(continentDto.getId());
            continent.setValue(continentDto.getValue());
            repository.save(continent);
        }
        return "OK";
    }

    public Optional<ContinentDto> findById(String id) {
        Optional<Continent> continentOptional = repository.findById(id);
        return continentOptional.map(this::convertToDto);
    }

    private ContinentDto convertToDto(Continent continent) {
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId(continent.getId());
        continentDto.setValue(continent.getValue());
        // Set other properties as needed
        return continentDto;
    }

    public void delete(String continentId) {
        repository.deleteById(continentId);
    }

}
