package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.exceptions.InvalidMapContentFormat;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import org.springframework.stereotype.Component;

import java.util.*;

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
        List<Country> countries = new ArrayList<>();

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

                    Optional<Continent> continent = getContinent(line);
                    continent.ifPresent(continents::add);

                } else if (territoriesFound && !line.isEmpty()) {

                    Optional<Country> country = getCountry(line);
                    country.ifPresent(countries::add);
                }
            }
        }

        MapFile mapFile = new MapFile();
        mapFile.setContinents(continents);
        mapFile.setCountries(countries);
        return mapFile;
    }

    private Optional<Country> getCountry(String line) {

        String[] l_splitCountry = line.split(",");
        if (l_splitCountry.length > 3) {
            String l_countryId = l_splitCountry[0];
            String l_continentId = l_splitCountry[3];

            Continent continent = new Continent();
            continent.setId(l_continentId);

            Country l_country = new Country();
            l_country.setId(l_countryId);
            l_country.setContinent(continent);

            if (l_splitCountry.length > 4) {
                for (int i = 4; i < l_splitCountry.length; i++) {
                    Country neighbor = new Country();
                    neighbor.setId(l_splitCountry[i]);
                    l_country.addNeighbor(neighbor);
                }
            }
            return Optional.of(l_country);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Continent> getContinent(String line) {

        String[] l_splitContinent = line.split("=");
        if (l_splitContinent.length >= 2) {
            String l_continentId = l_splitContinent[0];
            String l_continentValue = l_splitContinent[1];
            Continent continent = new Continent();
            continent.setId(l_continentId);
            continent.setValue(l_continentValue);
            return Optional.of(continent);
        } else {
            return Optional.empty();
        }
    }
}
