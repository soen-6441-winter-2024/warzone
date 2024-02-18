package ca.concordia.app.warzone.map;

import javax.swing.*;
import ca.concordia.app.warzone.map.exceptions.InvalidMapContentFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Represents the game map of the Warzone game.
 */
public class GameMap {
    private final Map<Integer, Country> d_countries = new HashMap<Integer, Country>(); // Stores a key-value pair of country ID as Key and country's object as value
    private final Map<Integer, Continent> d_continents = new HashMap<Integer, Continent>(); // Stores a key-value pair of continent ID as Key and Continent's object as value
    private final Map<Integer, Map<Integer, List<Integer>>> d_gameMap = new HashMap<Integer, Map<Integer, List<Integer>>>(); // Stores a key-value pair of Continent ID as Key and an inner HashMap with Country ID as Key and an ArrayList of connected/neighboring country IDs as value

    /**
     * Creates a new Continent object and adds it to the Game Map.
     *
     * @param p_name         the name of the continent
     * @param p_id           the unique identifier of the continent
     * @param p_bonusArmies  the bonus armies awarded for controlling the continent
     */
    public void addContinent(String p_name, int p_id, int p_bonusArmies) {
        if (d_continents.containsKey(p_id)) {
            System.out.println("Continent already exists" + "\n");
            return;
        }

        // create new continent object
        Continent continent = new Continent(p_name, p_id, p_bonusArmies);

        d_continents.put(p_id, continent);
        d_gameMap.put(p_id, new HashMap<>());
    }

    /**
     * Creates a new country object and adds it to the Game Map.
     *
     * @param p_name         the name of the country
     * @param p_countryId    the unique identifier of the country
     * @param p_continentId  the unique identifier of the continent to which the country belongs
     * @param p_numberOfArmies  the number of armies stationed in the country
     */
    public void addCountry(String p_name, int p_countryId, int p_continentId, int p_numberOfArmies) {
        // Check if continent exists or not
        if (!d_continents.containsKey(p_continentId)) {
            System.out.println("Continent with such ID not found" + "\n");
            return;
        }

        // Object of existing continent
        Continent continentToEdit = d_continents.get(p_continentId);

        // check if country already exists
        if (d_countries.containsKey(p_countryId)) {
            // Get country since it already exists
            Country country = d_countries.get(p_countryId);

            // check if country has been added under continent in GameMap
            if (d_gameMap.get(p_continentId).containsKey(p_countryId)) {
                System.out.println("Country \"" + country.getName() + "\" already exists in Continent "
                        + continentToEdit.getName());
                return;
            } else {
                // Put the existing country under the continent
                System.out.println("Country \"" + country.getName() + "\" has been added to Continent "
                        + continentToEdit.getName());
                d_gameMap.get(p_continentId).put(p_countryId, new ArrayList<>());
            }
        }

        // Since country doesn't previously exist, create one and add under the continent
        Country countryToAdd = new Country(p_countryId, p_numberOfArmies, p_name, p_continentId);

        d_countries.put(p_countryId, countryToAdd);
        this.d_gameMap.get(p_continentId).put(p_countryId, new ArrayList<>());
    }

    /**
     * Creates a directed edge/path from a country to a neighboring country.
     *
     * @param p_countryId         the unique identifier of the country
     * @param p_neighborCountryId the unique identifier of the neighboring country
     */
    public void addConnection(int p_countryId, int p_neighborCountryId) {
        // checks if country and neighbor country exists
        if (!d_countries.containsKey(p_countryId) || !d_countries.containsKey(p_neighborCountryId)) {
            System.out.println("Country with such ID not found");
            return;
        }

        Country country = d_countries.get(p_countryId);
        Continent continent = d_continents.get(country.getContinentId());

        // Add the neighboring country's ID to country's neighbors list
        d_gameMap.get(continent.getId()).get(p_countryId).add(p_neighborCountryId);
    }

    /**
     * The load map method takes in a file and looks for the corresponding file in the map_files directory, which can be found at the root of the project.
     * <br> The methods reads the domination game fall, validates and loads it's content into the Game Map.
     * @param p_fileName
     */
    public void loadMap(String p_fileName) {
        String filePath = "map_files" + File.separator + p_fileName;
        File file = new File(filePath);

        this.readAndLoadContinents(file);
        this.readAndLoadCountries(file);
        this.readAndLoadBorders(file);
    }

    private void readAndLoadContinents(File p_file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(p_file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals("[continents]")) {
                    while (scanner.hasNextLine()) {
                        String continent = scanner.nextLine();
                        String[] splitContinent = continent.split(" ");

                        if (continent.startsWith(";")) { // ignore comments in file
                            continue;
                        }

                        if (continent.isEmpty())
                            break;

                        int continentId = 0;
                        String continentName = "";
                        int bonusArmies = 0;

                        try {
                            continentId = Integer.parseInt(splitContinent[0]);
                            continentName = splitContinent[1];
                            bonusArmies = Integer.parseInt(splitContinent[2]);
                        } catch (Exception e) {
                            throw new InvalidMapContentFormat("Invalid Map: Continent format is invalid");
                        }

                        this.addContinent(continentName, continentId, bonusArmies);
                    }

                }
            }
        }
        catch (InvalidMapContentFormat e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Map not found in Maps directory");
            System.exit(0);
        } finally {
            try {
                scanner.close();
            } catch (NullPointerException e) {}

        }
    }

    private void readAndLoadCountries(File p_file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(p_file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals("[countries]")) {
                    while (scanner.hasNextLine()) {
                        String country = scanner.nextLine();

                        if (country.startsWith(";")) { // ignore comments in file
                            continue;
                        }

                        if (country.isEmpty())
                            break;

                        String[] splitCountryInfo = country.split(" ");

                        int countryId = Integer.parseInt(splitCountryInfo[0]);
                        String countryName = splitCountryInfo[1];
                        int continentId = Integer.parseInt(splitCountryInfo[2]);

                        this.addCountry(countryName, countryId, continentId, 0);
                    }

                }
            }
        } catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
            System.out.println("Map not found in Maps directory");
            System.exit(0);
        } finally {
            try {
                scanner.close();
            } catch (NullPointerException e) {}

        }
    }

    private void readAndLoadBorders(File p_file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(p_file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals("[borders]")) {
                    while (scanner.hasNextLine()) {
                        String borders = scanner.nextLine();

                        if (borders.startsWith(";")) { // ignore comments in file
                            continue;
                        }

                        if (borders.isEmpty())
                            break;

                        String[] splittedBorders = borders.split(" ");

                        int countryId = Integer.parseInt(splittedBorders[0]);

                        if(!d_countries.containsKey(countryId)) {
                            System.out.println("Invalid Country ID");
                            continue;
                        }

                        int countryContinentId = d_countries.get(countryId).getContinentId();

                        for (int i = 1; i < splittedBorders.length - 1; i++) {
                            d_gameMap.get(countryContinentId).get(countryId).add(Integer.parseInt(splittedBorders[i]));
                        }

                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Map not found in Map files directory");
            System.exit(0);
        } finally {
            try {
                scanner.close();
            } catch (NullPointerException e) {}

        }
    }

    public void showMap() {
        System.out.println();
        //TODO identify a possibly more efficient solution to show map
        for (Map.Entry<Integer, Map<Integer, List<Integer>>> continentEntry : d_gameMap.entrySet()) {
            Integer continentId = continentEntry.getKey();
            Continent continent = d_continents.get(continentId);
            System.out.println("Continent: " + continent.getName());

            Map<Integer, List<Integer>> countryMap = continentEntry.getValue();
            for (Map.Entry<Integer, List<Integer>> countryEntry : countryMap.entrySet()) {
                Integer countryId = countryEntry.getKey();
                Country country = d_countries.get(countryId);
                System.out.print(" - " + country.getName() + ", ID: " + country.getId() + ", Armies: "
                        + country.getNumberOfArmies() + ", Neighbors: ");

                for (Integer neighborCountryId : countryEntry.getValue()) {
                    System.out.print(d_countries.get(neighborCountryId).getName() + ", ");
                }
                System.out.println();
            }
        }
    }
}
