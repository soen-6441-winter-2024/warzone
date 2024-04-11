package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static ca.concordia.app.warzone.common.Util.validateParagraph;

/**
 * Conquest map file formatter which is in charge to format the map into a string and viceversa
 *
 */
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
            builder.append(continent.getId() + "=");
            builder.append(continent.getValue() + "\n");
        }
        builder.append("\n");

        builder.append("[Territories]");
        builder.append("\n");
        for (Country country : countries) {
            builder.append(country.getId() + ",,,");
            builder.append(country.getContinent().getId() + ",");
            String[] neighborIds = country.getNeighbors().stream().map(Country::getId).toArray(String[]::new);
            builder.append(String.join(",", neighborIds));
            builder.append("\n");
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

    private Optional<Country> getCountry(String p_line) {

        String[] l_splitCountry = p_line.split(",");
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

    private Optional<Continent> getContinent(String p_line) {

        String[] l_splitContinent = p_line.split("=");
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

    /**
     * Validate the lines of the file as CONQUEST format
     *
     * @param p_lines list of lines from the file
     * @return true if valid, false otherwise
     */
    public boolean validate(List<String> p_lines) {
        Iterator<String> iterator = p_lines.iterator();

        return validateParagraph(iterator, "Continents", this::isValidContinent) &&
                validateParagraph(iterator, "Territories", this::isValidTerritory);
    }

    /**
     * Validates if a line is a valid continent format
     *
     * @param p_line line of a file
     * @return true if valid, else false
     */
    private boolean isValidContinent(String p_line) {
        String[] words = p_line.split("=");
        // Invalid format: Each line after [Continents] should contain two
        // words separated by one equal symbol
        return words.length == 2;
    }

    /**
     * Validates if a line is a valid country format
     *
     * @param p_line line of a file
     * @return true if valid, else false
     */
    private boolean isValidTerritory(String p_line) {
        String[] words = p_line.split(",");
        // Invalid format: Each line after [Territories] should contain at least
        // four words separated by comma,
        return words.length >= 4;
    }
}
