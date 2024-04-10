package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Default Map file formatter component
 */
@Component
public class DefaultMapFileFormatter {
    /**
     * Constructor
     */
    public DefaultMapFileFormatter() {}
    /**
     * Generate the map content in the default format, given a list of continents and countries
     *
     * @param p_allContinents continents to be represented
     * @param p_allCountries countries to be represented
     * @return the content in the default format
     */
    public String process(List<Continent> p_allContinents, List<Country> p_allCountries) {
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("[continents]");
        builder.append("\n");
        for (Continent continent : p_allContinents) {
            builder.append(continent.getId() + " ");
            builder.append(continent.getValue() + "\n");
        }
        builder.append("\n");

        builder.append("[countries]");
        builder.append("\n");
        for (Country country : p_allCountries) {
            builder.append(country.getId() + " ");
            builder.append(country.getContinent().getId() + "\n");
        }

        builder.append("\n");
        builder.append("[borders]");
        builder.append("\n");
        for (Country country : p_allCountries) {
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
}
