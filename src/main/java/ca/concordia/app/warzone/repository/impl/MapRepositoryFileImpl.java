package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import ca.concordia.app.warzone.repository.MapRepository;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        p_map.setContinents(d_repoContinent.findAll());
        p_map.setCountries(d_repoCountry.findAll());

        if (!d_repoContinent.findAll().isEmpty()) {

            String content = d_defaultMapFormatter.mapToString(p_map);
            storeContent(p_map.getFileName(), content);

            return "Map was saved in the following filepath " + p_map.getFileName();
        } else {
            return "There are no continents to save";
        }
    }

    /**
     * method to store any string content in a given path
     *
     * @param p_filePath file path to store the content
     * @param p_content content to be stored
     */
    private void storeContent(String p_filePath, String p_content) {
        try {
            String directoryPath = p_filePath.substring(0, p_filePath.lastIndexOf(File.separator));

            // Create the directory if it doesn't exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new RuntimeException("Failed to create directory for map files");
                }
            }

            Path path = Paths.get(p_filePath);
            Files.writeString(path, p_content);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing the file", e);
        }
    }

    @Override
    public List<String> getMapAsString(String p_filePath) {

        try {
            Path path = Paths.get(p_filePath);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the file", e);
        }
    }

    @Override
    public MapFile getMap(String p_filePath) {

        List<String> lines = getMapAsString(p_filePath);

        return d_defaultMapFormatter.stringToMap(lines);
    }
}
