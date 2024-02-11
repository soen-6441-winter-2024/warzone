package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.controller.GameEngine;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapEditorShellComponent {

    private GameEngine controller = new GameEngine();

    public String editContinents(List<ContinentDto> continentsToAdd, List<String> continentsToDelete) {

//        controller.addContinent(continentsToAdd.get(0));
//        controller.addContinent(continentsToAdd.get(1ed));
//        controller.deleteContinent(continentsToDelete.get(0));

        return "OK";
    }

    public String editCountry(List<CountryDto> countryToAdd, List<String> countryToDelete) {
        return "result";
    }
}
