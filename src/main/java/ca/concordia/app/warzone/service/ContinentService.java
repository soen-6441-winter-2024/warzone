package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.model.Continent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing continents.
 */
@Service
public class ContinentService {

    private final ContinentRepository d_repository; // Data member for the ContinentRepository

    /**
     * Constructs a ContinentService with the specified ContinentRepository.
     *
     * @param p_repository the ContinentRepository to be used
     */
    public ContinentService(ContinentRepository p_repository) {
        this.d_repository = p_repository;
    }

    /**
     * Adds a continent to the repository.
     *
     * @param p_continentDto the DTO containing continent information
     * @return a message indicating success or failure
     */
    public String add(ContinentDto p_continentDto) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Adding Continent id=");
        result.append(p_continentDto.getId());
        result.append(" value=");
        result.append(p_continentDto.getValue());
        Optional<Continent> continentOptional = d_repository.findById(p_continentDto.getId());
        if (continentOptional.isPresent()) {
            result.append("\n");
            result.append("Continent id=");
            result.append(p_continentDto.getId());
            result.append(" already exists");
        } else {
            Continent continent = new Continent();
            continent.setId(p_continentDto.getId());
            continent.setValue(p_continentDto.getValue());
            d_repository.save(continent);
            result.append("\n");
            result.append("Continent id=");
            result.append(p_continentDto.getId());
            result.append(" value=");
            result.append(p_continentDto.getValue());
            result.append(" added successfully");
        }
        return result.toString();
    }

    /**
     * Finds a continent by its ID and converts it to DTO.
     *
     * @param p_id the ID of the continent to find
     * @return an optional containing the DTO if found, otherwise empty
     */
    public Optional<ContinentDto> findById(String p_id) {
        Optional<Continent> continentOptional = d_repository.findById(p_id);
        return continentOptional.map(this::convertToDto);
    }

    /**
     * Returns all continents
     * @return list of all continents
     */
    public List<Continent> findAll() {
        return this.d_repository.findAll();
    }

    /**
     * Deletes a continent by its ID.
     *
     * @param p_continentId the ID of the continent to delete
     * @return the result of the delete operation
     */
    public String delete(String p_continentId) {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        result.append("Deleting Continent id=");
        result.append(p_continentId);
        Optional<Continent> continentOptional = d_repository.findById(p_continentId);
        if (continentOptional.isPresent()) {
            d_repository.deleteById(p_continentId);
            result.append("\n");
            result.append("Continent id=");
            result.append(p_continentId);
            result.append(" deleted successfully");
        } else {
            result.append("\n");
            result.append("Continent id=");
            result.append(p_continentId);
            result.append(" doesn't exist");
        }

        return result.toString();
    }

    /**
     * Converts a Continent object to a ContinentDto object.
     *
     * @param p_continent the continent object to convert
     * @return the converted ContinentDto object
     */
    private ContinentDto convertToDto(Continent p_continent) {
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId(p_continent.getId());
        continentDto.setValue(p_continent.getValue());
        // Set other properties as needed
        return continentDto;
    }
}
