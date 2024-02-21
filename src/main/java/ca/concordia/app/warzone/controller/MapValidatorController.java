package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import org.springframework.stereotype.Component;

/**
 * Controller class for managing map editing operations.
 */
@Component
public class MapValidatorController {

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
    public MapValidatorController(ContinentService p_continentService, CountryService p_countryService, MapService p_mapService, PhaseRepository p_phaseRepository) {
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
        this.d_mapService = p_mapService;
        this.d_phaseRepository = p_phaseRepository;
    }

    /**
     * validates the loaded/created map to the output
     *
     * @return the result of the operation
     */
    public String validateMap() {
        return d_mapService.validate();
    }
}
