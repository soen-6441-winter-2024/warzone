package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class MapEditorController {
    private ContinentService continentService;
    private CountryService countryService;
    private MapService mapService;

    public MapEditorController(ContinentService continentService, CountryService countryService, MapService mapService) {
        this.continentService = continentService;
        this.countryService = countryService;
        this.mapService = mapService;
    }

    private Phase current = Phase.MAP_EDITOR;

    public String addContinent(ContinentDto continentDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return continentService.add(continentDto);
        }
        else {
            return "Invalid Phase";
        }
    }

    public String deleteContinent(String continentId) {
        if (Phase.MAP_EDITOR.equals(current)) {
            Optional<ContinentDto> continentOptional = continentService.findById(continentId);
            if (continentOptional.isPresent()) {
                continentService.delete(continentId);
                return "Continent deleted successfully.";
            } else {
                return "Continent not found.";
            }
        } else {
            return "Invalid Phase";
        }
    }

    public String addCountry(CountryDto countryDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return countryService.add(countryDto);
        }
        else {
            return "Invalid Phase";
        }
    }
    public String deleteCountry(String countryId) {
        if (Phase.MAP_EDITOR.equals(current)) {
            Optional<CountryDto> countryOptional = countryService.findById(countryId);
            if (countryOptional.isPresent()) {
                countryService.delete(countryId);
                return "Country deleted successfully.";
            } else {
                return "Country not found.";
            }
        } else {
            return "Invalid Phase";
        }
    }

    public String addNeighbor(CountryDto neighborDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return countryService.addNeighbor(neighborDto);
        }
        else {
            return "Invalid Phase";
        }
    }
    public String deleteNeighbor(CountryDto neighborDto) {
        if (Phase.MAP_EDITOR.equals(current)) {
            Optional<CountryDto> neighborOptional = countryService.findById(neighborDto.getId());
            if (neighborOptional.isPresent()) {
                countryService.deleteNeighbor(neighborDto);
                return "Neighbor Country deleted successfully.";
            } else {
                return "Neighbor Country  not found.";
            }
        } else {
            return "Invalid Phase";
        }
    }

    public String saveMap(MapDto mapDto) {

        if (Phase.MAP_EDITOR.equals(current)) {
            return mapService.saveMap(mapDto);
        }
        else {
            return "Invalid Phase";
        }
    }
}
