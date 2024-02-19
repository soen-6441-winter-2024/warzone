package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.exceptions.InvalidMapContentFormat;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.service.model.Continent;
import ca.concordia.app.warzone.service.model.Country;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Service class for managing map-related operations.
 */
@Service
public class MapService {
    private final CountryRepository d_repoCountry; // Data member for the CountryRepository
    private final ContinentRepository d_repoContinent; // Data member for the ContinentRepository
    private final PlayerService d_playerService;


    /**
     * Constructs a MapService with the specified CountryRepository and
     * ContinentRepository.
     *
     * @param p_repoCountry the CountryRepository to be used
     * @param p_repoContinent the ContinentRepository to be used
     */
    public MapService(CountryRepository p_repoCountry, ContinentRepository p_repoContinent, PlayerService p_playerService) {
        this.d_repoCountry = p_repoCountry;
        this.d_repoContinent = p_repoContinent;
        this.d_playerService = p_playerService;
    }

    /**
     * The load map method takes in a file and looks for the corresponding file in
     * the map_files directory, which can be found at the root of the project.
     * <br>
     * The methods reads the domination game fall, validates and loads it's content
     * into the Game Map.
     * 
     * @param p_mapDto
     */
    public String loadMap(MapDto p_mapDto) {
        String p_fileName = p_mapDto.getFileName();

        String filePath = "map_files" + File.separator + p_fileName;
        File file = new File(filePath);

            this.readAndLoadContinents(file);
            this.readAndLoadCountries(file);
            this.readAndLoadBorders(file);

            return p_mapDto.getFileName() + " Map file loaded";
    }

    /**
     * Reads the .map file, retrieves the continents and creates them respectively
     * 
     * @param p_file
     */
    private void readAndLoadContinents(File p_file) {
        try (Scanner scanner = new Scanner(p_file)) {
            ContinentService l_continentService = new ContinentService(d_repoContinent);

            while (scanner.hasNextLine()) {
                String l_line = scanner.nextLine();

                if (l_line.equals("[continents]")) {

                    while (scanner.hasNextLine()) {
                        String l_continentInfo = scanner.nextLine();
                        String[] l_splitContinent = l_continentInfo.split(" ");

                        if (l_continentInfo.startsWith(";")) { // ignore comments in file
                            continue;
                        }
                        if (l_continentInfo.isEmpty())
                            break;

                        String l_continentId = "";
                        String l_continentName = "";
                        int l_bonusArmies = 0;

                        try {
                            l_continentId = l_splitContinent[0];
                            l_continentName = l_splitContinent[1];
                            l_bonusArmies = Integer.parseInt(l_splitContinent[2]);
                        } catch (Exception e) {
                            throw new InvalidMapContentFormat("Invalid Map: Continent format is invalid");
                        }

                        ContinentDto continentDto = new ContinentDto();
                        continentDto.setId(l_continentId);
                        continentDto.setValue(l_continentName);
                        continentDto.set_bonusArmies(l_bonusArmies);

                        l_continentService.add(continentDto);
                    }

                }
            }
        } catch (InvalidMapContentFormat e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Map not found in Maps directory \\\"./map_files\\\".");
            System.exit(0);
        }
    }

    /**
     * Reads the .map file, retrieves the countries and creates them respectively
     * 
     * @param p_file
     */
    private void readAndLoadCountries(File p_file) {
        try (Scanner scanner = new Scanner(p_file)) {
            ContinentService l_continentService = new ContinentService(d_repoContinent);
            CountryService l_countryService = new CountryService(d_repoCountry, l_continentService);

            while (scanner.hasNextLine()) {
                String l_line = scanner.nextLine();
                if (l_line.equals("[countries]")) {
                    while (scanner.hasNextLine()) {
                        String country = scanner.nextLine();
                        if (country.startsWith(";")) { // ignore comments in file
                            continue;
                        }
                        if (country.isEmpty())
                            break;

                        String[] splitCountryInfo = country.split(" ");
                        String l_countryId = splitCountryInfo[0];
                        String l_countryName = splitCountryInfo[1];
                        String l_continentId = splitCountryInfo[2];

                        Optional<ContinentDto> continentDtoOptional = l_continentService.findById(l_continentId);

                        if (!continentDtoOptional.isPresent())
                            throw new InvalidMapContentFormat("Invalid Map: Continent not found.");

                        CountryDto l_countryDto = new CountryDto();
                        l_countryDto.setId(l_countryId);
                        l_countryDto.setName(l_countryName);
                        l_countryDto.setContinent(continentDtoOptional.get());

                        l_countryService.add(l_countryDto);
                    }

                }
            }
        } catch (InvalidMapContentFormat e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Map not found in Maps directory \"./map_files\".");
            System.exit(0);
        }
    }

    private void readAndLoadBorders(File p_file) {
        try (Scanner scanner = new Scanner(p_file)) {
            ContinentService l_continentService = new ContinentService(d_repoContinent);
            CountryService l_countryService = new CountryService(d_repoCountry, l_continentService);

            while (scanner.hasNextLine()) {
                String l_line = scanner.nextLine();

                if (l_line.equals("[borders]")) {
                    while (scanner.hasNextLine()) {
                        String l_borders = scanner.nextLine();

                        if (l_borders.startsWith(";")) { // ignore comments in file
                            continue;
                        }

                        if (l_borders.isEmpty())
                            break;

                        String[] splittedBorders = l_borders.split(" ");
                        // store the ID of the country to add neighbors to
                        String countryId = splittedBorders[0];

                        // Retrieve the data transfer object for the country
                        Optional<CountryDto> countryDto = l_countryService.findById(countryId);
                        if (!countryDto.isPresent())
                            throw new InvalidMapContentFormat("Invalid Map: Country not found.");

                        // add neighboring countries to the country
                        for (int i = 1; i < splittedBorders.length; i++) {
                            countryDto.get().setNeighborId(splittedBorders[i]);

                            l_countryService.addNeighbor(countryDto.get());
                        }

                    }

                }
            }
        } catch (InvalidMapContentFormat e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Map not found in Maps directory \"./map_files\".");
        }
    }

    public String showMap() {
        System.out.println();
        // retrieve lists of all continents and countries in memory
        List<Continent> l_allContinents = d_repoContinent.findAll();
        List<Country> l_allCountries = d_repoCountry.findAll();

        if (l_allContinents.isEmpty() && l_allCountries.isEmpty())
            return "No map found: Map has not been created or loaded";

        for (Continent l_continent : l_allContinents) {
            System.out.println("Continent: " + l_continent.getValue() + ", Continent ID: " + l_continent.getId());

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
            return "There are no map elements to save";
        }
    }


    public void assignCountries() {
        List<Player> players = d_playerService.getAllPlayers();
        List<Country> countries = d_repoCountry.findAll();

        Collections.shuffle(countries);

        int totalPlayers = players.size();
        int minCountriesPerPlayer = countries.size() / totalPlayers;
        int remainingCountries = countries.size() % totalPlayers;
        int i = 0;

        // Distribute the countries evenly among players
        for (Player player : players) {
            for (int j = 0; j < minCountriesPerPlayer; j++) {
                player.addCountry(countries.get(i));
                System.out.println(player.getPlayerName() + " was assigned " + countries.get(i));
                i++;
            }
        }

        // Distribute remaining countries
        for (int j = 0; j < remainingCountries; j++) {
            Player player = players.get(j);
            player.addCountry(countries.get(i));
            System.out.println(player.getPlayerName() + " was assigned " + countries.get(i));
            i++;
        }
    }
}
