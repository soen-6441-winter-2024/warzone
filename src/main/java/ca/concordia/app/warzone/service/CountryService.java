package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing countries.
 */
@Service
public class CountryService {

    private final CountryRepository d_repoCountry; // Data member for the CountryRepository
    private final ContinentService d_continentService; // Data member for the ContinentService

    /**
     * Constructs a CountryService with the specified CountryRepository and ContinentService.
     *
     * @param p_repoCountry the CountryRepository to be used
     * @param p_continentService the ContinentService to be used
     */
    public CountryService(CountryRepository p_repoCountry, ContinentService p_continentService) {
        this.d_repoCountry = p_repoCountry;
        this.d_continentService = p_continentService;
    }

    /**
     * Adds a country to the repository.
     *
     * @param p_dto the DTO containing country information
     * @return a message indicating success or failure
     */
    public String add(CountryDto p_dto) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Adding Country id=");
        result.append(p_dto.getId());
        result.append(" Continent id=");
        result.append(p_dto.getContinent().getId());
        Optional<ContinentDto> continentDtoOptional = d_continentService.findById(p_dto.getContinent().getId());
        if (continentDtoOptional.isPresent()) {
            Optional<Country> countryOptional = d_repoCountry.findById(p_dto.getId());
            if (countryOptional.isPresent()) {
                result.append("\n");
                result.append("Country id=");
                result.append(p_dto.getId());
                result.append(" Continent id=");
                result.append(p_dto.getContinent().getId());
                result.append(" already exists");
            } else {
                ContinentDto continentDto = continentDtoOptional.get();

                Continent continent = new Continent();
                continent.setId(continentDto.getId());
                continent.setValue(continentDto.getValue());

                Country country = new Country();
                country.setId(p_dto.getId());
                country.setContinent(continent);

                d_repoCountry.save(country);
                result.append("\n");
                result.append("Country id=");
                result.append(p_dto.getId());
                result.append(" Continent id=");
                result.append(p_dto.getContinent().getId());
                result.append(" added successfully");
            }
        } else {
            result.append("\n");
            result.append("Continent id=");
            result.append(p_dto.getContinent().getId());
            result.append(" doesn't exist");
        }
        return result.toString();
    }

    /**
     * Finds a country by its ID and converts it to DTO.
     *
     * @param p_id the ID of the country to find
     * @return an optional containing the DTO if found, otherwise empty
     */
    public Optional<CountryDto> findById(String p_id) {
        Optional<Country> countryOptional = d_repoCountry.findById(p_id);
        return countryOptional.map(this::convertToDto);
    }

    /**
     * Returns all the countries
     * @return all the countries
     */
    public List<Country> findAll() {
        return this.d_repoCountry.findAll();
    }

    /**
     * Returns all the countries that belong to the specified continent
     * @param p_continentId continent id to look for
     * @return The list of countries that belong to the specified continent
     */
    public List<Country> findByContinentId(String p_continentId) {
        return this.d_repoCountry.findByContinentId(p_continentId);
    }
    /**
     * Converts a Country object to a CountryDto object.
     *
     * @param p_country the country object to convert
     * @return the converted CountryDto object
     */
    private CountryDto convertToDto(Country p_country) {
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId(p_country.getContinent().getId());
        continentDto.setValue(p_country.getContinent().getValue());

        List<CountryDto> neighborsDto = new ArrayList<>();
        for (Country neighbor : p_country.getNeighbors()) {
            ContinentDto continentDtoNeighbor = new ContinentDto();
            continentDtoNeighbor.setId(neighbor.getContinent().getId());
            continentDtoNeighbor.setValue(neighbor.getContinent().getValue());
            CountryDto countryDtoNeighbor = new CountryDto();
            countryDtoNeighbor.setId(neighbor.getId());
            countryDtoNeighbor.setContinent(continentDtoNeighbor);
            neighborsDto.add(countryDtoNeighbor);
        }

        CountryDto countryDto = new CountryDto();
        countryDto.setId(p_country.getId());
        countryDto.setContinent(continentDto);
        countryDto.setNeighbors(neighborsDto);

        return countryDto;
    }

    /**
     * Deletes a country by its ID.
     *
     * @param p_countryId the ID of the country to delete
     * @return the result of the operation
     */
    public String delete(String p_countryId) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Deleting Country id=");
        result.append(p_countryId);
        Optional<Country> countryOptional = d_repoCountry.findById(p_countryId);
        if (countryOptional.isPresent()) {
            d_repoCountry.deleteById(p_countryId);
            result.append("\n");
            result.append("Country id=");
            result.append(p_countryId);
            result.append(" deleted successfully");
        } else{
            result.append("\n");
            result.append("Country id=");
            result.append(p_countryId);
            result.append(" doesn't exist");
        }

        return result.toString();
    }

    /**
     * Adds a neighbor country to the specified country.
     *
     * @param p_dto the DTO containing country and neighbor information
     * @return a message indicating success or failure
     */
    public String addNeighbor(CountryDto p_dto) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Adding Neighbor Country Country id=");
        result.append(p_dto.getId());
        result.append(" Country Neighbor id=");
        result.append(p_dto.getNeighborId());

        Optional<Country> countryOptional = d_repoCountry.findById(p_dto.getId());
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            List<Country> neighbors = new ArrayList<>();
            for (Country neighbor : country.getNeighbors()) {
                Optional<Country> neighborOptional = d_repoCountry.findById(neighbor.getId());
                if (neighborOptional.isPresent()) {
                    neighbors.add(neighbor);
                }
            }
            Optional<Country> newNeighborOptional = d_repoCountry.findById(p_dto.getNeighborId());
            if (newNeighborOptional.isPresent()) {
                Country newNeighborCountry = newNeighborOptional.get();
                neighbors.add(newNeighborCountry);
            }

            country.setNeighbors(neighbors);
            d_repoCountry.save(country);
            result.append("\n");
            result.append("Neighbor Country Country id=");
            result.append(p_dto.getId());
            result.append(" Country Neighbor id=");
            result.append(p_dto.getNeighborId());
            result.append(" added successfully");
        } else {
            result.append("\n");
            result.append("Country id=");
            result.append(p_dto.getId());
            result.append(" does not exist");
        }

        return result.toString();
    }

    /**
     * Deletes a neighbor country from the specified country.
     *
     * @param p_dto the DTO containing country and neighbor information
     * @return the result of the operation
     */
    public String deleteNeighbor(CountryDto p_dto) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Deleting Neighbor Country Country id=");
        result.append(p_dto.getId());
        result.append(" Country Neighbor id=");
        result.append(p_dto.getNeighborId());

        Optional<Country> countryOptional = d_repoCountry.findById(p_dto.getId());
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            List<Country> neighbors = new ArrayList<>();
            for (Country neighbor : country.getNeighbors()) {
                Optional<Country> neighborOptional = d_repoCountry.findById(neighbor.getId());
                if (neighborOptional.isPresent()) {
                    if (!neighbor.getId().equals(p_dto.getNeighborId())) {
                        neighbors.add(neighbor);
                    }
                }
            }
            country.setNeighbors(neighbors);
            d_repoCountry.deleteNeighborById(country);
            result.append("\n");
            result.append("Neighbor Country Country id=");
            result.append(p_dto.getId());
            result.append(" Country Neighbor id=");
            result.append(p_dto.getNeighborId());
            result.append(" deleted successfully");
        }
        else {
            result.append("\n");
            result.append("Neighbor Country Country id=");
            result.append(p_dto.getId());
            result.append(" Country Neighbor id=");
            result.append(p_dto.getNeighborId());
            result.append(" doesn't exist");
        }
        return result.toString();
    }

    /**
     * Deploys armies to a country
     * @param p_countryId the id of the country
     * @param p_count the number of armies
     */
    public void addArmiesToCountry(String p_countryId, int p_count) {
        this.d_repoCountry.findById(p_countryId).ifPresent(country -> {
            int armiesCount = country.getArmiesCount();
            country.setArmiesCount(armiesCount + p_count);
            d_repoCountry.save(country);
        });
    }
}
