package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.exceptions.InvalidMapContentFormat;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Conquest map file formatter which is in charge to format the map into a string and viceversa
 *
 */
@Component
public class ConquestMapFileFormatter {

    /**
     * Converts the map into string Conquest map format
     *
     * @param p_mapFile map object containing information countries and continents
     * @return map as string ready to store in a file
     */
    public String mapToString(MapFile p_mapFile) {
        StringBuilder builder = new StringBuilder();

        List<Continent> continents = p_mapFile.getContinents();
        List<Country> countries = p_mapFile.getCountries();

        builder.append("[Continents]");
        builder.append("\n");
        for (Continent continent : continents) {
            builder.append(continent.getId() + " ");
            builder.append(continent.getValue() + "\n");
        }
        builder.append("\n");

        builder.append("[Territories]");
        builder.append("\n");
        for (Country country : countries) {
            builder.append(country.getId() + " ");
            builder.append(country.getContinent().getId() + "\n");
        }

        builder.append("\n");
        builder.append("[borders]");
        builder.append("\n");
        for (Country country : countries) {
            // Assuming each country can have multiple neighbors
            List<Country> allNeighborsByCountry = country.getNeighbors();
            if (!allNeighborsByCountry.isEmpty()) {
                builder.append(country.getId());
                for (Country neighbor : allNeighborsByCountry) {
                    builder.append(" ");
                    builder.append(neighbor.getId());
                }
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * Generate the map given a list of lines by a conquest format
     *
     * @param p_lines lines of the map in conquest format
     * @return the map as object
     */
    public MapFile stringToMap(List<String> p_lines) {
        List<Continent> continents = new ArrayList<>();
        Map<String, Country> countries = new HashMap<>();

        boolean continentsFound = false;
        boolean territoriesFound = false;

        for (String line : p_lines) {
            line = line.trim(); // Remove leading and trailing whitespace
            if (line.equals("[Continents]")) {
                continentsFound = true;
                territoriesFound = false;
            } else if (line.equals("[Territories]")) {
                territoriesFound = true;
                continentsFound = false;
            } else {
                if (continentsFound && !line.isEmpty()) {
                    String[] l_splitContinent = line.split("\\s+");
                    if (l_splitContinent.length == 2) {
                        String l_continentId = l_splitContinent[0];
                        String l_continentValue = l_splitContinent[1];
                        Continent continent = new Continent();
                        continent.setId(l_continentId);
                        continent.setValue(l_continentValue);
                        continents.add(continent);
                    }
                } else if (territoriesFound && !line.isEmpty()) {
                    String[] l_splitCountry = line.split("\\s+");
                    if (l_splitCountry.length == 2) {
                        String l_countryId = l_splitCountry[0];
                        String l_continentId = l_splitCountry[1];

                        Continent continent = new Continent();
                        continent.setId(l_continentId);

                        Country l_country = new Country();
                        l_country.setId(l_countryId);
                        l_country.setContinent(continent);
                        countries.put(l_countryId, l_country);
                    }
//                } else if (bordersFound && !line.isEmpty()) {
                    String[] l_splitNeighbor = line.split("\\s+");
                    String l_countryId = l_splitNeighbor[0];

                    if (countries.containsKey(l_countryId)) {

                        Country country = countries.get(l_countryId);

                        // add neighboring countries to the country
                        for (int i = 1; i < l_splitNeighbor.length; i++) {
                            Country neighbor = new Country();
                            neighbor.setId(l_splitNeighbor[i]);
                            country.addNeighbor(neighbor);
                        }
                    } else {
                        throw new InvalidMapContentFormat("Invalid Map: Country Id=" + l_countryId + " not found.");
                    }
                }
            }
        }

        MapFile mapFile = new MapFile();
        mapFile.setContinents(continents);
        mapFile.setCountries(new ArrayList<>(countries.values()));
        return mapFile;
    }
}
