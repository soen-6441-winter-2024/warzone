package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
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
     *
     * @param p_id id of the country
     * @return oprional containing the country if found
     */
    public Optional<Country> findCountryById(String p_id) {
        return d_repoCountry.findById(p_id);
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

        if (p_country.getPlayer().isPresent()) {
            PlayerDto playerDto = new PlayerDto();
            Player player = p_country.getPlayer().get();
            playerDto.setPlayerName(player.getPlayerName());
            playerDto.setCountriesAssigned(player.getCountriesAssigned().stream().map(Country::getId).toList());
            countryDto.setPlayer(playerDto);
        }

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
     * Checks if 2 countries are neighbors
     * @param first the first country
     * @param second the second country
     * @return true if they are neighbors, false otherwise.
     * @throws NotFoundException when country isnt found
     */
    public boolean areNeighbors(String first, String second) throws NotFoundException {
        Optional<Country> countryOpt = d_repoCountry.findById(first);
        if(!countryOpt.isPresent()) {
            throw new NotFoundException("country does not exist");
        }

        Optional<Country> secondCountryOpt = d_repoCountry.findById(second);
        if(!secondCountryOpt.isPresent()) {
            throw new NotFoundException("country does not exist");
        }

        return countryOpt.get().getNeighbors().contains(secondCountryOpt.get());
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
    /**
     * Bombs a country causing it to lose half of it's armies.
     * @param p_countryId the id of the country
     */
    public void bombACountry(String p_countryId) {
        this.d_repoCountry.findById(p_countryId).ifPresent(
            country -> {
                int armiesCount = country.getArmiesCount();
                country.setArmiesCount((int) (armiesCount / 2));
                d_repoCountry.save(country);
            }
        );
    }

    /**
     * removes speciied number of armies
     * @param p_countryId the id of the country from which armies are to be removed
     * @param p_count the count of armies to be removed
     */
    public void removeArmiesFromCountry(String p_countryId, int p_count) {
        this.d_repoCountry.findById(p_countryId).ifPresent(country -> {
            int armiesCount = country.getArmiesCount();
            country.setArmiesCount(armiesCount - p_count);
            d_repoCountry.save(country);
        });
    }

    /**
     * Sets number of armies for each country with the specified id
     * @param p_countryId the id of the country
     * @param p_count the new count of armies for the country
     */
    public void setArmiesCountToCountry(String p_countryId, int p_count) {
        this.d_repoCountry.findById(p_countryId).ifPresent(country -> {
            country.setArmiesCount(p_count);
        });
    }
}
