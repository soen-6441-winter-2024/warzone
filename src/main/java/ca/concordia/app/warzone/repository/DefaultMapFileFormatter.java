package ca.concordia.app.warzone.repository;

import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultMapFileFormatter {
    public String process(List<Continent> allContinents, List<Country> allCountries) {
        
        StringBuilder builder = new StringBuilder();
        
        builder.append("[continents]");
        builder.append("\n");
        for (Continent continent : allContinents) {
            builder.append(continent.getId() + " ");
            builder.append(continent.getValue() + "\n");
        }
        builder.append("\n");

        builder.append("[countries]");
        builder.append("\n");
        for (Country country : allCountries) {
            builder.append(country.getId() + " ");
            builder.append(country.getContinent().getId() + "\n");
        }

        builder.append("\n");
        builder.append("[borders]");
        builder.append("\n");
        for (Country country : allCountries) {
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
