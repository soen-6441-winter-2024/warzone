package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.phase.MapEditorPhase;
import org.springframework.stereotype.Component;
import ca.concordia.app.warzone.logging.LoggingService;

/**
 * Controller class for managing map editing operations.
 */
@Component
public class MapEditorController {

    private ContinentService d_continentService;
    private CountryService d_countryService;
    private MapService d_mapService;
    private PhaseRepository d_phaseRepository;

    /**
     * Constructs a MapEditorController with the specified services.
     *
     * @param p_continentService The ContinentService to use.
     * @param p_countryService   The CountryService to use.
     * @param p_mapService       The MapService to use.
     * @param p_phaseRepository  The PhaseRepository to use.
     */
    public MapEditorController(ContinentService p_continentService, CountryService p_countryService, MapService p_mapService, PhaseRepository p_phaseRepository) {
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
        this.d_mapService = p_mapService;
        this.d_phaseRepository = p_phaseRepository;
    }

    /**
     * Adds a continent to the map.
     *
     * @param p_continentDto The ContinentDto representing the continent to add.
     * @return A string indicating the result of the operation.
     */
    public String addContinent(ContinentDto p_continentDto) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =   d_continentService.add(p_continentDto);
        } else {
            result = "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }
    /**
     * Deletes a continent from the map.
     *
     * @param p_continentId The ID of the continent to delete.
     * @return A string indicating the result of the operation.
     */
    public String deleteContinent(String p_continentId) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =  d_continentService.delete(p_continentId);
        } else {
            result =  "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Adds a country to the map.
     *
     * @param p_countryDto The CountryDto representing the country to add.
     * @return A string indicating the result of the operation.
     */
    public String addCountry(CountryDto p_countryDto) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =  d_countryService.add(p_countryDto);
        } else {
            result =  "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Deletes a country from the map.
     *
     * @param p_countryId The ID of the country to delete.
     * @return A string indicating the result of the operation.
     */
    public String deleteCountry(String p_countryId) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =  d_countryService.delete(p_countryId);
        } else {
            result =  "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Adds a neighbor country to a country on the map.
     *
     * @param p_neighborDto The CountryDto representing the neighbor country to add.
     * @return A string indicating the result of the operation.
     */
    public String addNeighbor(CountryDto p_neighborDto) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =  d_countryService.addNeighbor(p_neighborDto);
        } else {
            result =  "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Deletes a neighbor country from a country on the map.
     *
     * @param p_neighborDto The CountryDto representing the neighbor country to delete.
     * @return A string indicating the result of the operation.
     */
    public String deleteNeighbor(CountryDto p_neighborDto) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =  d_countryService.deleteNeighbor(p_neighborDto);
        } else {
            result =  "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Saves the map.
     *
     * @param p_mapDto The MapDto representing the map to save.
     * @return A string indicating the result of the operation.
     */
    public String saveMap(MapDto p_mapDto) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result =  d_mapService.saveMap(p_mapDto);
        } else {
            result =  "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * Loads the map file.
     *
     * @param p_mapDto The MapDto representing the map to save.
     * @return A string indicating the result of the operation.
     */
    public String loadMap(MapDto p_mapDto) {
       String result = d_phaseRepository.getPhase().loadMap(p_mapDto);
        LoggingService.log(result);
        return result;
    }

    /**
     * Edits the map file.
     *
     * @param p_mapDto The MapDto representing the map to save.
     * @return A string indicating the result of the operation.
     */
    public String editMap(MapDto p_mapDto) {
        String result = "";
        if (d_phaseRepository.getPhase() instanceof MapEditorPhase) {
            result = d_mapService.editMap(p_mapDto);
        } else {
            result = "Invalid Phase";
        }
        LoggingService.log(result);
        return result;
    }

    /**
     * shows the map to the output
     *
     * @return the result of the operation
     */
    public String showMap() {
        String result = d_mapService.showMap();
        LoggingService.log(result);
        return result;
    }
}
