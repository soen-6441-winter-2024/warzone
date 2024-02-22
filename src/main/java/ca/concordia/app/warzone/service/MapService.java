package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.exceptions.InvalidMapContentFormat;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing map-related operations.
 */
@Service
public class MapService {
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

    /**
     * Constructs a MapService with the specified CountryRepository and
     * ContinentRepository.
     *
     * @param p_repoCountry   the CountryRepository to be used
     * @param p_repoContinent the ContinentRepository to be used
     */
    public MapService(CountryRepository p_repoCountry, ContinentRepository p_repoContinent) {
        this.d_repoCountry = p_repoCountry;
        this.d_repoContinent = p_repoContinent;
    }

    /**
     * Loads a map from the specified file path.
     *
     * @param p_mapDto the DTO containing map file information
     * @return a message indicating success or failure
     */
    public String loadMap(MapDto p_mapDto) {
        String filePath = p_mapDto.getFileName();
        if (validateMapStructure(filePath)) {
            this.readAndLoadMap(filePath);
            return p_mapDto.getFileName() + " Map file loaded";
        } else {
            return "Map file didn't pass format validation";
        }
    }

    /**
     * Edits a map from the specified file path.
     *
     * @param p_mapDto the DTO containing map file information
     * @return a message indicating success or failure
     */
    public String editMap(MapDto p_mapDto) {
        String filePath = p_mapDto.getFileName();
        if (validateEditMapStructure(filePath)) {
            if (this.readAndLoadMap(filePath)) {
                return p_mapDto.getFileName() + " Map file loaded";
            }
            else
            {
                return "Map file wasn't loaded, Creating a new empty Map, Now you can enter EditContinent, EditCountry and Edit Neighbor Commands to create your map from teh Scratch";
            }
        } else {
            return "Map file didn't pass format validation";
        }
    }

    /**
     * Validates the structure of the map file.
     *
     * @param p_file the path of the map file
     * @return true if the map file structure is valid, false otherwise
     */
    public boolean validateMapStructure(String p_file) {
        boolean resultValidation = true;
        boolean continentsFound = false;
        boolean countriesFound = false;
        boolean bordersFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(p_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace

                if (line.equals("[continents]")) {
                    continentsFound = true;
                    countriesFound = false;
                    bordersFound = false;
                } else if (line.equals("[countries]")) {
                    if (!continentsFound) {
                        resultValidation = false;
                        return resultValidation; // Invalid structure: [countries] section before [continents]
                    }
                    countriesFound = true;
                    continentsFound = false;
                    bordersFound = false;
                } else if (line.equals("[borders]")) {
                    if (!countriesFound) {
                        resultValidation = false;
                        return resultValidation; // Invalid structure: [borders] section before [countries]
                    }
                    bordersFound = true;
                    countriesFound = false;
                    continentsFound = false;
                } else {
                    // Validate content format
                    if (continentsFound && !line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length != 2) {
                            resultValidation = false;
                            return resultValidation; // Invalid format: Each line after [continents] should contain two
                                                     // elements separated by one blank space
                        }
                    } else if (countriesFound && !line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length != 2) {
                            resultValidation = false;
                            return resultValidation; // Invalid format: Each line after [countries] should contain two
                                                     // elements separated by one blank space
                        }
                    } else if (bordersFound && !line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length <= 1) {
                            resultValidation = false;
                            return resultValidation; // Invalid format: Each line after [borders] should contain at
                                                     // least two elements or more separated by one blank space
                        }
                    }
                }
            }
            return resultValidation;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
            return false; // Error reading file
        }
    }

    /**
     * Validates the structure of the map file for the Edit Map Functionality.
     *
     * @param p_file the path of the map file
     * @return true if the map file structure is valid, false otherwise
     */
    public boolean validateEditMapStructure(String p_file) {
        File file = new File(p_file);
        if (!file.exists()) {
            System.out.println("File not found: " + p_file);
            return true;
        }

        boolean resultValidation = true;
        boolean continentsFound = false;
        boolean countriesFound = false;
        boolean bordersFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(p_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace

                if (line.equals("[continents]")) {
                    continentsFound = true;
                    countriesFound = false;
                    bordersFound = false;
                } else if (line.equals("[countries]")) {
                    if (!continentsFound) {
                        resultValidation = false;
                        return resultValidation; // Invalid structure: [countries] section before [continents]
                    }
                    countriesFound = true;
                    continentsFound = false;
                    bordersFound = false;
                } else if (line.equals("[borders]")) {
                    if (!countriesFound) {
                        resultValidation = false;
                        return resultValidation; // Invalid structure: [borders] section before [countries]
                    }
                    bordersFound = true;
                    countriesFound = false;
                    continentsFound = false;
                } else {
                    // Validate content format
                    if (continentsFound && !line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length != 2) {
                            resultValidation = false;
                            return resultValidation; // Invalid format: Each line after [continents] should contain two elements separated by one blank space
                        }
                    } else if (countriesFound && !line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length != 2) {
                            resultValidation = false;
                            return resultValidation; // Invalid format: Each line after [countries] should contain two elements separated by one blank space
                        }
                    } else if (bordersFound && !line.isEmpty()) {
                        String[] parts = line.split("\\s+");
                        if (parts.length <= 1) {
                            resultValidation = false;
                            return resultValidation; // Invalid format: Each line after [borders] should contain at least two elements or more separated by one blank space
                        }
                    }
                }
            }
            return resultValidation;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false; // Error reading file
        }
    }

    /**
     * Reads and loads the map from the specified file.
     *
     * @param p_file the path of the map file
     * @return true if there is at least one continent loaded, false otherwise
     */
    private boolean readAndLoadMap(String p_file) {
        boolean loadResult = true;
        boolean continentsFound = false;
        boolean countriesFound = false;
        boolean bordersFound = false;

        ContinentService l_continentService = new ContinentService(d_repoContinent);
        CountryService l_countryService = new CountryService(d_repoCountry, l_continentService);

        try (BufferedReader reader = new BufferedReader(new FileReader(p_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.equals("[continents]")) {
                    continentsFound = true;
                    countriesFound = false;
                    bordersFound = false;
                } else if (line.equals("[countries]")) {
                    countriesFound = true;
                    continentsFound = false;
                    bordersFound = false;
                } else if (line.equals("[borders]")) {
                    bordersFound = true;
                    countriesFound = false;
                    continentsFound = false;
                } else {
                    if (continentsFound && !line.isEmpty()) {
                        String[] l_splitContinent = line.split("\\s+");
                        if (l_splitContinent.length == 2) {
                            String l_continentId = l_splitContinent[0];
                            String l_continentValue = l_splitContinent[1];
                            ContinentDto continentDto = new ContinentDto();
                            continentDto.setId(l_continentId);
                            continentDto.setValue(l_continentValue);
                            l_continentService.add(continentDto);
                        }
                    } else if (countriesFound && !line.isEmpty()) {
                        String[] l_splitCountry = line.split("\\s+");
                        if (l_splitCountry.length == 2) {
                            String l_countryId = l_splitCountry[0];
                            String l_continentId = l_splitCountry[1];
                            Optional<ContinentDto> continentDtoOptional = l_continentService.findById(l_continentId);

                            if (continentDtoOptional.isEmpty())
                                throw new InvalidMapContentFormat(
                                        "Invalid Map: Continent Id=" + l_continentId + " not found.");

                            CountryDto l_countryDto = new CountryDto();
                            l_countryDto.setId(l_countryId);
                            l_countryDto.setContinent(continentDtoOptional.get());
                            l_countryService.add(l_countryDto);
                        }
                    } else if (bordersFound && !line.isEmpty()) {
                        String[] l_splitNeighbor = line.split("\\s+");
                        String l_countryId = l_splitNeighbor[0];
                        Optional<CountryDto> countryDto = l_countryService.findById(l_countryId);
                        if (countryDto.isEmpty())
                            throw new InvalidMapContentFormat("Invalid Map: Country Id=" + l_countryId + " not found.");

                        // add neighboring countries to the country
                        for (int i = 1; i < l_splitNeighbor.length; i++) {
                            countryDto.get().setNeighborId(l_splitNeighbor[i]);
                            l_countryService.addNeighbor(countryDto.get());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //System.exit(0);
        } catch (InvalidMapContentFormat e) {
            System.out.println(e.getMessage());
            //System.exit(0);
        }
        if (l_continentService.findAll().isEmpty())
        {
            loadResult =false;
        }
        return loadResult;
    }

    /**
     * Shows the current state of the map.
     *
     * @return a message indicating success or failure
     */
    public String showMap() {
        System.out.println();
        // retrieve lists of all continents and countries in memory
        List<Continent> l_allContinents = d_repoContinent.findAll();
        List<Country> l_allCountries = d_repoCountry.findAll();

        if (l_allContinents.isEmpty() && l_allCountries.isEmpty())
            return "No map found: Map has not been created or loaded";

        for (Continent l_continent : l_allContinents) {
            System.out.println("Continent ID: " + l_continent.getId() + ", Continent Value: " + l_continent.getValue());

            for (Country country : l_allCountries) {
                if (country.getContinent().getId().equals(l_continent.getId())) {
                    System.out.print(" - Country: " + country.getId() + ", Owner: "
                            + (country.getPlayer().isEmpty() ? "Not yet assigned"
                                    : country.getPlayer().get().getPlayerName())
                            + ", Neighbors: ");

                    for (Country neighborCountry : country.getNeighbors()) {
                        System.out.print(neighborCountry.getId() + ", ");
                    }
                    System.out.println();
                }
            }
        }

        return "Map Printed";
    }

    /**
     * Saves the map to a file.
     *
     * @param p_dto the DTO containing map information
     * @return a message indicating success or failure
     */
    public String saveMap(MapDto p_dto) {
        String filePath = p_dto.getFileName();
        String directoryPath = filePath.substring(0, filePath.lastIndexOf(File.separator));

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return "Failed to create directory for map files";
            }
        }

        List<Continent> allContinents = d_repoContinent.findAll();
        List<Country> allCountries = d_repoCountry.findAll();
        if (!allContinents.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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
                        writer.write(country.getContinent().getId() + "\n");
                    }
                } else {
                    return "There are no countries to save";
                }
                writer.write("\n");
                if (!allCountries.isEmpty()) {
                    writer.write("[borders]");
                    writer.write("\n");
                    for (Country country : allCountries) {
                        // Assuming each country can have multiple neighbors
                        List<Country> allNeighborsByCountry = country.getNeighbors();
                        if (!allNeighborsByCountry.isEmpty()) {
                            writer.write(country.getId());
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
                return "Failed to save map";
            }
            return "Map was saved in the following filepath " + filePath;
        } else {
            return "There are no continents to save";
        }
    }

    /**
     * Validates if the whole map and the individual continents of a load or created 
     * map in memory are connected graphs
     * @return The state of the validated map
     */
    public String validate() {
        Map<Integer, Map<Integer, List<Integer>>> l_map = new HashMap<Integer, Map<Integer, List<Integer>>>();

        List<Continent> allContinents = d_repoContinent.findAll();
        List<Country> allCountries = d_repoCountry.findAll();

        if(allContinents.isEmpty() && allCountries.isEmpty()) return "No map found. Create or load a map to validate.";

        for(Continent continent : allContinents) {
            Integer l_continentId = Integer.parseInt(continent.getId());
            l_map.put(l_continentId, new HashMap<>());

            for (Country country : allCountries) {
                Integer countryId = Integer.parseInt(country.getId());

                if(country.getContinent().getId().equals(continent.getId())) {
                    List<Integer> neighborsId = new ArrayList<>();

                    for (Country neighbor : country.getNeighbors()) {
                        Integer neighborId = Integer.parseInt(neighbor.getId());
        
                        neighborsId.add(neighborId);
                    }

                    l_map.get(l_continentId).put(countryId, neighborsId);
                }
            }
        }

        boolean isConnectedMap = isConnectedMap(l_map);
        String l_result = "";

        if(isConnectedMap) l_result += "\nAll continents are connected.";
        else l_result += "\nNot all continents are connected.";


        for (int continentID : l_map.keySet()) {
            boolean isConnectedContinent = isConnectedContinent(l_map, continentID);
            if(isConnectedContinent) {
                l_result += "\nContinent ID: " + continentID + " is a connected subgraph";
            } else l_result += "\nContinent ID: " + continentID + " is NOT a connected subgraph";
        }

        return l_result;
    }
    /**
     * Validates is there's a valid path between continents (sub-graphs) of the map (graph)
     * @param p_map
     * @return returns true if there exists a path connecting all continents
     */
    private static boolean isConnectedMap(Map<Integer, Map<Integer, List<Integer>>> p_map) {
        Set<Integer> visited = new HashSet<>();
        dfsTraversal(p_map, p_map.keySet().iterator().next(), visited);
        return visited.size() == getTotalCountries(p_map);
    }
    /**
     * 
     * @param p_map
     * @param p_currentCountry
     * @param visited
     */
    private static void dfsTraversal(Map<Integer, Map<Integer, List<Integer>>> p_map, int p_currentCountry,
            Set<Integer> visited) {
        if (visited.contains(p_currentCountry)) {
            return;
        }
        visited.add(p_currentCountry);
        Map<Integer, List<Integer>> continent = getContinentContainingCountry(p_map, p_currentCountry);
        if (continent != null) {
            for (int neighbor : continent.get(p_currentCountry)) {
                dfsTraversal(p_map, neighbor, visited);
            }
        }
    }
    /**
     * Validates if continents are connected.
     * @param p_map
     * @param p_continentID
     * @return true if there exists an edge connecting continents
     */
    private static boolean isConnectedContinent(Map<Integer, Map<Integer, List<Integer>>> p_map, int p_continentID) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Integer>> continent = p_map.get(p_continentID);

        if (continent == null || continent.isEmpty()) {
            return false; // Continent does not exist or is empty
        }
        int startCountry = continent.keySet().iterator().next();
        dfsTraversalContinent(p_map, startCountry, visited, p_continentID);

        if (visited.size() == continent.size()) {
            return true;
        }
        return false;
    }
    /**
     * Traverses the map graph using a depth first search approach to validate valid edges between continents
     * @param p_map
     * @param p_currentCountry
     * @param p_visited
     * @param p_continentID
     */
    private static void dfsTraversalContinent(Map<Integer, Map<Integer, List<Integer>>> p_map, int p_currentCountry,
            Set<Integer> p_visited, int p_continentID) {
        if (p_visited.contains(p_currentCountry)) {
            return;
        }
        p_visited.add(p_currentCountry);
        Map<Integer, List<Integer>> continent = p_map.get(p_continentID);
        if (continent != null && continent.containsKey(p_currentCountry)) {
            for (int neighbor : continent.get(p_currentCountry)) {
                dfsTraversalContinent(p_map, neighbor, p_visited, p_continentID);
            }
        }
    }
    /**
     * @param p_map
     * @param p_country
     * @return Returns continent ID of country passed
     */
    private static Map<Integer, List<Integer>> getContinentContainingCountry(
            Map<Integer, Map<Integer, List<Integer>>> p_map, int p_country) {
        for (Map<Integer, List<Integer>> l_continent : p_map.values()) {
            if (l_continent.containsKey(p_country)) {
                return l_continent;
            }
        }
        return null;
    }
    /**
     * Returns the total number of countries in the map
     * @param p_map
     * @return the total number of countries
     */
    private static int getTotalCountries(Map<Integer, Map<Integer, List<Integer>>> p_map) {
        int l_total = 0;
        for (Map<Integer, List<Integer>> continent : p_map.values()) {
            l_total += continent.size();
        }
        return l_total;
    }
}
