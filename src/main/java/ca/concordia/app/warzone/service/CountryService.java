package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.service.model.Continent;
import ca.concordia.app.warzone.service.model.Country;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class CountryService {

    private final CountryRepository repoCountry;
    private final ContinentService continentService;

    public CountryService(CountryRepository repoCountry, ContinentService continentService) {
        this.repoCountry = repoCountry;
        this.continentService = continentService;
    }

    public String add(CountryDto dto) {
        Optional<ContinentDto> continentDtoOptional = continentService.findById(dto.getContinent().getId());
        if (continentDtoOptional.isPresent()) {
            Optional<Country> countryOptional = repoCountry.findById(dto.getId());
            if (countryOptional.isPresent()) {
                return "Country already exists";
            }
            else {
                ContinentDto continentDto = continentDtoOptional.get();

                Continent continent = new Continent();
                continent.setId(continentDto.getId());
                continent.setValue(continentDto.getValue());

                /*List<Country> neighbors = new ArrayList<>();
                for (CountryDto neighborDto : dto.getNeighbors()) {
                    Continent continentNeighbor = new Continent();
                    continentNeighbor.setId(neighborDto.getContinent().getId());
                    continentNeighbor.setValue(neighborDto.getContinent().getValue());
                    Country neighborCountry = new Country();
                    neighborCountry.setId(neighborDto.getId());
                    neighborCountry.setContinent(continentNeighbor);
                    neighbors.add(neighborCountry);
                }*/

                Country country = new Country();
                country.setId(dto.getId());
                country.setContinent(continent);
                //country.setNeighbors(neighbors);

                repoCountry.save(country);
                return "OK";
            }
        } else {
            return "Continent doesn't exist";
        }
    }

    public Optional<CountryDto> findById(String id) {
        Optional<Country> countryOptional = repoCountry.findById(id);
        return countryOptional.map(this::convertToDto);
    }

    private CountryDto convertToDto(Country country) {
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId(country.getContinent().getId());
        continentDto.setValue(country.getContinent().getValue());

        List<CountryDto> neighborsDto = new ArrayList<>();
        for (Country neighbor : country.getNeighbors()) {
            ContinentDto continentDtoNeighbor = new ContinentDto();
            continentDtoNeighbor.setId(neighbor.getContinent().getId());
            continentDtoNeighbor.setValue(neighbor.getContinent().getValue());
            CountryDto countryDtoNeighbor = new CountryDto();
            countryDtoNeighbor.setId(neighbor.getId());
            countryDtoNeighbor.setContinent(continentDtoNeighbor);
            neighborsDto.add(countryDtoNeighbor);
        }

        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setContinent(continentDto);
        countryDto.setNeighbors(neighborsDto);

        return countryDto;
    }
    public void delete(String countryId) {
        repoCountry.deleteById(countryId);
    }

    public String addNeighbor(CountryDto dto) {
        Optional<Country> countryOptional = repoCountry.findById(dto.getId());
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            List<Country> neighbors = new ArrayList<>();
            for (Country neighbor : country.getNeighbors()) {
                Optional<Country> neighborOptional = repoCountry.findById(neighbor.getId());
                if (neighborOptional.isPresent()) {
                    neighbors.add(neighbor);
                }
            }
            Optional<Country> newNeighborOptional = repoCountry.findById(dto.getNeighborId());
            if (newNeighborOptional.isPresent()) {
                Country newNeighborCountry = newNeighborOptional.get();
                neighbors.add(newNeighborCountry);
            }

            country.setNeighbors(neighbors);
            repoCountry.save(country);
            return "OK";
        }
        else {
            return "Country does not exists";
        }
    }
    public void deleteNeighbor(CountryDto dto) {
        Optional<Country> countryOptional = repoCountry.findById(dto.getId());
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            List<Country> neighbors = new ArrayList<>();
            for (Country neighbor : country.getNeighbors()) {
                Optional<Country> neighborOptional = repoCountry.findById(neighbor.getId());
                if (neighborOptional.isPresent()) {
                    if (!neighbor.getId().equals(dto.getNeighborId()))
                    {
                        neighbors.add(neighbor);
                    }
                }
            }
            country.setNeighbors(neighbors);
            repoCountry.deleteNeighborById(country);
        }
    }

}
