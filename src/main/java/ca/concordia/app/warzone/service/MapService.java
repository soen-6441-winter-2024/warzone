package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.service.model.Continent;
import ca.concordia.app.warzone.service.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
@Service
public class MapService {
    private final CountryRepository repoCountry;
    private final ContinentRepository repoContinent;

    public MapService(CountryRepository repoCountry, ContinentRepository repoContinent) {
        this.repoCountry = repoCountry;
        this.repoContinent = repoContinent;
    }

    public String saveMap(MapDto dto) {
        String fileName = dto.getFileName();
        List<Continent> allContinents = repoContinent.findAll();
        List<Country> allCountries = repoCountry.findAll();
        if (!allContinents.isEmpty()) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
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
                        writer.write(country.getContinent().getId() + " ");
                        writer.write(country.getContinent().getValue() + "\n");
                    }
                }
                writer.write("\n");

                if (!allCountries.isEmpty()) {
                    writer.write("[borders]");
                    writer.write("\n");
                    for (Country country : allCountries) {
                        writer.write(country.getId() + " ");
                        // Assuming each country can have multiple neighbors
                        for (Country neighbor : country.getNeighbors()) {
                            if (!allCountries.isEmpty()) {
                                writer.write(" ");
                                writer.write(neighbor.getId() + "\n");
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Map was saved in the following filepath " + fileName;
        }
        else {
            return "there are no map elements to save";
        }
    }
}
