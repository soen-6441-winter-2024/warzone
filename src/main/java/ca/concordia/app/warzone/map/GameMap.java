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

public class GameMap {
    private final Map<Integer, Country> countries = new HashMap<Integer, Country>(); // Stores a key-value pair of country ID as Key and country's object as value
    private final Map<Integer, Continent> continents = new HashMap<Integer, Continent>(); // Stores a key-value pair of continent ID as Key and Continent's object as value

    /*
     * gameMap is a HashMap with a key-value pair of Continent ID as Key and an
     * inner HashMap with Country ID as Key and an ArrayList of
     * connected/neighboring country IDs as value
     */
    private final Map<Integer, Map<Integer, List<Integer>>> gameMap = new HashMap<Integer, Map<Integer, List<Integer>>>();

    /**
     * Creates a new Continent objects and adds it to the Game Map
     * @param name
     * @param id
     * @param bonusArmies
     */
    public void addContinent(String name, int id, int bonusArmies) {
        if (continents.containsKey(id)) {
            System.out.println("Continent already exists" + "\n");
            return;
        }

        // create new continent object
        Continent continent = new Continent(name, id, bonusArmies);

        continents.put(id, continent);
        gameMap.put(id, new HashMap<>());
    }

    /**
     * Creates a new country object and adds it to the Game Map 
     * @param name
     * @param countryId
     * @param continentId
     * @param numberOfArmies
     */
    public void addCountry(String name, int countryId, int continentId, int numberOfArmies) {
        // Check if continent exists or not
        if (!continents.containsKey(continentId)) {
            System.out.println("Continent with such ID not found" + "\n");
            return;
        }

        // Object of existing continent
        Continent continentToEdit = continents.get(continentId);

        // check if country already exists
        if (countries.containsKey(countryId)) {
            // Get country since it already exists
            Country country = countries.get(countryId);

            // check if country has been added under continent in GameMap
            if (gameMap.get(continentId).containsKey(countryId)) {
                System.out.println("Country \"" + country.getName() + "\" already exists in Continent "
                        + continentToEdit.getName());
                return;
            } else {
                // Put the existing country under the continent
                System.out.println("Country \"" + country.getName() + "\" has been added to Continent "
                        + continentToEdit.getName());
                gameMap.get(continentId).put(countryId, new ArrayList<>());
            }
        }

        // Since country doesn't previously exist, create one and add under the continent
        Country countryToAdd = new Country(countryId, numberOfArmies, name, continentId);

        countries.put(countryId, countryToAdd);
        this.gameMap.get(continentId).put(countryId, new ArrayList<>());
    }
    
    /**
     * Methods takes in the IDs of a country and neighboring country. It then creates a directed edge/path from the the country to neighbor country. 
     * 
     * @param countryId
     * @param neighborCountryId
     */
    public void addConnection(int countryId, int neighborCountryId) {
        // checks if country and neighbor country exists
        if (!countries.containsKey(countryId) || !countries.containsKey(neighborCountryId)) {
            System.out.println("Country with such ID not found");
            return;
        }

        Country country = countries.get(countryId);
        Continent continent = continents.get(country.getContinentId());

        // Add the neighboring country's ID to country's neighbors list
        gameMap.get(continent.getId()).get(countryId).add(neighborCountryId);
    }

    /**
     * The load map method takes in a file and looks for the corresponding file in the map_files directory, which can be found at the root of the project.
     * <br> The methods reads the domination game fall, validates and loads it's content into the Game Map.
     * @param fileName
     */
    public void loadMap(String fileName) {
        String filePath = "map_files" + File.separator + fileName;
        File file = new File(filePath);

        this.readAndLoadContinents(file);
        this.readAndLoadCountries(file);
        this.readAndLoadBorders(file);
    }

    private void readAndLoadContinents(File file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

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

    private void readAndLoadCountries(File file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

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

    private void readAndLoadBorders(File file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

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

                        if(!countries.containsKey(countryId)) {
                            System.out.println("Invalid Country ID");
                            continue;
                        }

                        int countryContinentId = countries.get(countryId).getContinentId();

                        for (int i = 1; i < splittedBorders.length - 1; i++) {
                            gameMap.get(countryContinentId).get(countryId).add(Integer.parseInt(splittedBorders[i]));
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
        for (Map.Entry<Integer, Map<Integer, List<Integer>>> continentEntry : gameMap.entrySet()) {
            Integer continentId = continentEntry.getKey();
            Continent continent = continents.get(continentId);
            System.out.println("Continent: " + continent.getName());

            Map<Integer, List<Integer>> countryMap = continentEntry.getValue();
            for (Map.Entry<Integer, List<Integer>> countryEntry : countryMap.entrySet()) {
                Integer countryId = countryEntry.getKey();
                Country country = countries.get(countryId);
                System.out.print(" - " + country.getName() + ", ID: " + country.getId() + ", Armies: "
                        + country.getNumberOfArmies() + ", Neighbors: ");

                for (Integer neighborCountryId : countryEntry.getValue()) {
                    System.out.print(countries.get(neighborCountryId).getName() + ", ");
                }
                System.out.println();
            }
        }
    }
}
