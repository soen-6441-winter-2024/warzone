package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import ca.concordia.app.warzone.repository.DefaultMapFileFormatter;
import ca.concordia.app.warzone.repository.MapRepository;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implementation of the MapRepository interface
 */
@Repository
public class MapRepositoryFileImpl implements MapRepository {

    /**
     * Data member for storing the CountryRepository, for fetching and storing
     * countries.
     */
    private final CountryRepository d_repoCountry; // Data member for the CountryRepository

    /**
     * Data member for storing the ContinentRepository, for fetching and storing
     * continents.
     */
    private final ContinentRepository d_repoContinent; // Data member for the ContinentRepository

    private final DefaultMapFileFormatter d_defaultMapFormatter;

    /**
     * Constructs a MapRepositoryMemoryImpl object with the specified CountryRepository and ContinentRepository.
     *
     * @param p_repoCountry   the CountryRepository to be used
     * @param p_repoContinent the ContinentRepository to be used
     * @param p_defaultMapFormatter the default map file formatter to be used
     */
    public MapRepositoryFileImpl(CountryRepository p_repoCountry, ContinentRepository p_repoContinent, DefaultMapFileFormatter p_defaultMapFormatter) {
        this.d_repoCountry = p_repoCountry;
        this.d_repoContinent = p_repoContinent;
        this.d_defaultMapFormatter = p_defaultMapFormatter;
    }

    /**
     * Saves a map as a file.
     *
     * @param p_map the map file to save
     * @return a message indicating whether the map was saved successfully or not
     */
    @Override
    public String saveMap(MapFile p_map) {
        String filePath = p_map.getFileName();

        List<Continent> allContinents = d_repoContinent.findAll();
        List<Country> allCountries = d_repoCountry.findAll();

        if (!allContinents.isEmpty()) {

            String content = d_defaultMapFormatter.process(allContinents, allCountries);
            storeMap(filePath, content);

            return "Map was saved in the following filepath " + filePath;
        } else {
            return "There are no continents to save";
        }
    }

    private void storeMap(String filePath, String content) {
        try {
            String directoryPath = filePath.substring(0, filePath.lastIndexOf(File.separator));

            // Create the directory if it doesn't exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new RuntimeException("Failed to create directory for map files");
                }
            }

            Path path = Paths.get(filePath);
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing the file", e);
        }
    }

    @Override
    public List<String> getMap(String p_filePath) {

        try {
            Path path = Paths.get(p_filePath);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the file", e);
        }
    }
}
