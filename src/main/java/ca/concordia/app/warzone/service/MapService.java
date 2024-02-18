package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.service.model.Continent;
import ca.concordia.app.warzone.service.model.Country;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Service class for managing map-related operations.
 */
@Service
public class MapService {

    private final CountryRepository d_repoCountry; // Data member for the CountryRepository
    private final ContinentRepository d_repoContinent; // Data member for the ContinentRepository

    /**
     * Constructs a MapService with the specified CountryRepository and ContinentRepository.
     *
     * @param p_repoCountry the CountryRepository to be used
     * @param p_repoContinent the ContinentRepository to be used
     */
    public MapService(CountryRepository p_repoCountry, ContinentRepository p_repoContinent) {
        this.d_repoCountry = p_repoCountry;
        this.d_repoContinent = p_repoContinent;
    }

    /**
     * Saves the map to a file.
     *
     * @param p_dto the DTO containing map information
     * @return a message indicating success or failure
     */
    public String saveMap(MapDto p_dto) {
        String fileName = p_dto.getFileName();
        List<Continent> allContinents = d_repoContinent.findAll();
        List<Country> allCountries = d_repoCountry.findAll();
        if (!allContinents.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("[continents]");
                writer.write("\n");
                for (Continent continent : allContinents) {
                    writer.write(continent.getId() + " ");
                    writer.write(continent.getValue() + "\n");
                }
                writer.write("\n");
                if (!allCountries.isEmpty()) {
                    writer.write("[countries]");
                    writer.write("\n");
                    for (Country country : allCountries) {
                        writer.write(country.getId() + " ");
                        writer.write(country.getContinent().getId() + " ");
                        writer.write(country.getContinent().getValue() + "\n");
                    }
                }
                writer.write("\n");
                if (!allCountries.isEmpty()) {
                    writer.write("[borders]");
                    writer.write("\n");
                    for (Country country : allCountries) {
                        // Assuming each country can have multiple neighbors
                        List<Country> allNeighborsByCountry =country.getNeighbors();
                        if (!allNeighborsByCountry.isEmpty()) {
                            writer.write(country.getId() + " ");
                            for (Country neighbor : allNeighborsByCountry) {
                                writer.write(" ");
                                writer.write(neighbor.getId());
                            }
                            writer.write("\n");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Map was saved in the following filepath " + fileName;
        } else {
            return "There are no map elements to save";
        }
    }
}
