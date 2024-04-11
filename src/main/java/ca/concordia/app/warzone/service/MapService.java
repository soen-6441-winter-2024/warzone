package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.exceptions.InvalidMapContentFormat;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import ca.concordia.app.warzone.repository.MapRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

/**
 * Service class for managing map-related operations.
 */
@Service
public class MapService {
    /**
     * Data member for storing the MapRepository, for fetching and storing
     * countries.
     */
    private final MapRepository d_repoMap;
    /**
     * Data member for storing the CountryService, for fetching and storing
     * countries.
     */
    private final CountryService d_countryService;

    /**
     * Data member for storing the ContinentService, for fetching and storing
     * continents.
     */
    private final ContinentService d_continentService;
    
    /**
     * Data member for storing name of game map
     */
    private String d_mapName;

    /**
     * Get map name
     * @return map name
     */
    public String getMapName() {
        return this.d_mapName;
    }
    
    /**
     * Constructs a MapService with the specified MapRepository, CountryService, and ContinentService.
     *
     * @param p_repoMap   the MapRepository to be used
     * @param p_countryService   the CountryService to be used
     * @param p_continentService the ContinentService to be used
     */
    public MapService(MapRepository p_repoMap, CountryService p_countryService, ContinentService p_continentService) {
        this.d_repoMap = p_repoMap;
        this.d_countryService = p_countryService;
        this.d_continentService = p_continentService;
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
            this.d_mapName = p_mapDto.getFileName();
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

        List<String> mapLines = d_repoMap.getMapAsString(p_file);
        Iterator<String> iterator = mapLines.iterator();

        return validateParagraph(iterator, "continents", this::isValidContinent) &&
                validateParagraph(iterator, "countries", this::isValidCountry) &&
                validateParagraph(iterator, "borders", this::isValidBorder);
    }

    /**
     * Validates is a paragraph is formatted correctly
     *
     * @param p_iterator Iterator of the lines
     * @param p_title Title of the paragraph
     * @param p_lineValidator Predicate method to validate each line
     * @return true if valid, else false.
     */
    protected boolean validateParagraph(Iterator<String> p_iterator, String p_title, Predicate<String> p_lineValidator) {

        String line = getFirstNonEmptyLine(p_iterator);

        if (p_iterator.hasNext() && line.equals("[" + p_title +"]")) {
            line = p_iterator.next();
        } else {
            return false;
        }

        while (!line.trim().isEmpty()){

            if (!p_lineValidator.test(line)) {
                return false;
            }

            if (p_iterator.hasNext()) {
                line = p_iterator.next();
            } else {
                break;
            }
        }

        return true;
    }

    /**
     * Get the first non-empty line from the lines of the file
     *
     * @param p_iterator Iterator referencing the lines of the file
     * @return Non-empty line
     */
    private String getFirstNonEmptyLine(Iterator<String> p_iterator) {

        String line = "";

        while (p_iterator.hasNext() && line.trim().isEmpty()) {
            line = p_iterator.next();
        }

        return line;
    }

    /**
     * Validates if a line is a valid continent format
     *
     * @param p_line line of a file
     * @return true if valid, else false
     */
    private boolean isValidContinent(String p_line) {
        String[] words = p_line.split("\\s+");
        // Invalid format: Each line after [continents] should contain two
        // words separated by one blank space
        return words.length == 2;
    }

    /**
     * Validates if a line is a valid country format
     *
     * @param p_line line of a file
     * @return true if valid, else false
     */
    private boolean isValidCountry(String p_line) {
        String[] words = p_line.split("\\s+");
        // Invalid format: Each line after [countries] should contain two
        // words separated by one blank space
        return words.length == 2;
    }

    /**
     * Validates if a line is a valid border format
     *
     * @param p_line line of a file
     * @return true if valid, else false
     */
    private boolean isValidBorder(String p_line) {
        String[] words = p_line.split("\\s+");
        // Invalid format: Each line after [borders] should contain at
        // least two words or more separated by one blank space
        return words.length >= 2;
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

        return validateMapStructure(p_file);
    }

    /**
     * Reads and loads the map from the specified file.
     *
     * @param p_file the path of the map file
     * @return true if there is at least one continent loaded, false otherwise
     */
    protected boolean readAndLoadMap(String p_file) {

        MapFile map = d_repoMap.getMap(p_file);
        return loadMap(map);
    }

    /**
     * Loads the map into the system.
     *
     * @param p_map map contents
     * @return true if there is at least one continent loaded, false otherwise
     */
    protected boolean loadMap(MapFile p_map) {
        try {
            saveContinents(p_map.getContinents());
            saveCountries(p_map.getCountries());
            saveNeighbors(p_map.getCountries());
        } catch (InvalidMapContentFormat e) {
            return false;
        }
        return !d_continentService.findAll().isEmpty();
    }

    /**
     * Save neighbor countries in the system
     *
     * @param p_countries country list with neighbors to save
     */
    private void saveNeighbors(List<Country> p_countries) {
        for (Country country : p_countries) {
            for (Country neighbor : country.getNeighbors()) {
                CountryDto countryDto = mapCountryDto(country);
                countryDto.setNeighborId(neighbor.getId());
                d_countryService.addNeighbor(countryDto);
            }
        }
    }

    /**
     * Save countries in the system
     *
     * @param p_countries country list to save
     */
    private void saveCountries(List<Country> p_countries) {
        for (Country country : p_countries) {
            Optional<ContinentDto> continentDtoOptional = d_continentService.findById(country.getContinent().getId());

            if (continentDtoOptional.isEmpty()) {
                throw new InvalidMapContentFormat(
                        "Invalid Map: Continent Id=" + country.getContinent().getId() + " not found.");
            }

            CountryDto countryDto = mapCountryDto(country);
            d_countryService.add(countryDto);
        }
    }

    /**
     * Save continents in the system
     *
     * @param p_continents continent list to save
     */
    private void saveContinents(List<Continent> p_continents) {
        for (Continent continent : p_continents) {
            d_continentService.add(mapContinentDto(continent));
        }
    }

    /**
     * map country as dto
     *
     * @param p_country country object
     * @return country dto
     */
    private CountryDto mapCountryDto(Country p_country) {
        CountryDto dto = new CountryDto();
        dto.setId(p_country.getId());
        dto.setContinent(mapContinentDto(p_country.getContinent()));
        return dto;
    }

    /**
     * map continent as dto
     *
     * @param p_continent continent object
     * @return continent dto
     */
    private ContinentDto mapContinentDto(Continent p_continent) {
        ContinentDto dto = new ContinentDto();
        dto.setId(p_continent.getId());
        dto.setValue(p_continent.getValue());
        return dto;
    }

    /**
     * Shows the current state of the map.
     *
     * @return a message indicating success or failure
     */
    public String showMap() {
        System.out.println();
        // retrieve lists of all continents and countries in memory
        List<Continent> l_allContinents = d_continentService.findAll();
        List<Country> l_allCountries = d_countryService.findAll();

        if (l_allContinents.isEmpty() && l_allCountries.isEmpty())
            return "No map found: Map has not been created or loaded.";

        StringBuilder result = new StringBuilder();

        for (Continent l_continent : l_allContinents) {
            result.append("Continent ID: " + l_continent.getId() + ", Continent Value: " + l_continent.getValue() +  "\n");

            for (Country country : l_allCountries) {
                if (country.getContinent().getId().equals(l_continent.getId())) {
                    result.append(" - Country: " + country.getId() + " Armies: " + country.getArmiesCount() + ", Owner: "
                            + (country.getPlayer().isEmpty() ? "Not yet assigned"
                                    : country.getPlayer().get().getPlayerName())
                            + ", Neighbors:");

                    for (Country neighborCountry : country.getNeighbors()) {
                        result.append(" " + neighborCountry.getId() + ",");
                    }
                    result.append("\n");
                }
            }
        }

        return result.toString();
    }

    /**
     * Saves the map to a file.
     *
     * @param p_dto the DTO containing map information
     * @return a message indicating success or failure if the map file was saved
     */
    public String saveMap(MapDto p_dto) {
        MapFile mapFile = new MapFile();
        mapFile.setFileName(p_dto.getFileName());

        String result = "";
        try {
            result = d_repoMap.saveMap(mapFile);
            this.d_mapName = p_dto.getFileName();
        } catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    /**
     * Validates if the whole map and the individual continents of a load or created 
     * map in memory are connected graphs
     * @return The state of the validated map
     */
    public String validate() {
        Map<String, Map<String, List<String>>> l_map = new HashMap<String, Map<String, List<String>>>();

        List<Continent> allContinents = d_continentService.findAll();
        List<Country> allCountries = d_countryService.findAll();

        if(allContinents.isEmpty() && allCountries.isEmpty()) return "No map found. Create or load a map to validate.";

        for(Continent continent : allContinents) {
            String l_continentId = continent.getId();
            l_map.put(l_continentId, new HashMap<>());

            for (Country country : allCountries) {
                String countryId = country.getId();

                if(country.getContinent().getId().equals(continent.getId())) {
                    List<String> neighborsId = new ArrayList<>();

                    for (Country neighbor : country.getNeighbors()) {
                        String neighborId = neighbor.getId();
        
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


        for (String continentID : l_map.keySet()) {
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
    private static boolean isConnectedMap(Map<String, Map<String, List<String>>> p_map) {
        Set<String> visited = new HashSet<>();
        dfsTraversal(p_map, p_map.keySet().iterator().next(), visited);
        return visited.size() == getTotalCountries(p_map);
    }
    /**
     * 
     * @param p_map
     * @param p_currentCountry
     * @param visited
     */
    private static void dfsTraversal(Map<String, Map<String, List<String>>> p_map, String p_currentCountry,
            Set<String> visited) {
        if (visited.contains(p_currentCountry)) {
            return;
        }
        visited.add(p_currentCountry);
        Map<String, List<String>> continent = getContinentContainingCountry(p_map, p_currentCountry);
        if (continent != null) {
            for (String neighbor : continent.get(p_currentCountry)) {
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
    private static boolean isConnectedContinent(Map<String, Map<String, List<String>>> p_map, String p_continentID) {
        Set<String> visited = new HashSet<>();
        Map<String, List<String>> continent = p_map.get(p_continentID);

        if (continent == null || continent.isEmpty()) {
            return false; // Continent does not exist or is empty
        }
        String startCountry = continent.keySet().iterator().next();
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
    private static void dfsTraversalContinent(Map<String, Map<String, List<String>>> p_map, String p_currentCountry,
            Set<String> p_visited, String p_continentID) {
        if (p_visited.contains(p_currentCountry)) {
            return;
        }
        p_visited.add(p_currentCountry);
        Map<String, List<String>> continent = p_map.get(p_continentID);
        if (continent != null && continent.containsKey(p_currentCountry)) {
            for (String neighbor : continent.get(p_currentCountry)) {
                dfsTraversalContinent(p_map, neighbor, p_visited, p_continentID);
            }
        }
    }
    /**
     * @param p_map
     * @param p_country
     * @return Returns continent ID of country passed
     */
    private static Map<String, List<String>> getContinentContainingCountry(
            Map<String, Map<String, List<String>>> p_map, String p_country) {
        for (Map<String, List<String>> l_continent : p_map.values()) {
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
    private static int getTotalCountries(Map<String, Map<String, List<String>>> p_map) {
        int l_total = 0;
        for (Map<String, List<String>> continent : p_map.values()) {
            l_total += continent.size();
        }
        return l_total;
    }

    /**
     * Get continent size
     *
     * @param allContinents list of continents
     */
    public void getContinentSize(List<Continent> allContinents){

        for(Continent continent : allContinents){

            int sizeOfContinent = getCountriesByContinentId(continent.getId());
            continent.setSizeOfContinent(sizeOfContinent);
        }
    }

    /**
     * Get countries by continent
     *
     * @param continentId continent id
     * @return number of countries
     */
    public int getCountriesByContinentId(String continentId){
        List<Country> l_allCountries = d_countryService.findAll();
        int sizeOfContinent = 0;
        for(Country country : l_allCountries){
            if(country.getContinent().getId().equals(continentId)){
                sizeOfContinent++;
            }
        }
        return  sizeOfContinent;
    }
}
