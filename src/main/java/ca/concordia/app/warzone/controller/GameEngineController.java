package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.MapService;
import ca.concordia.app.warzone.service.PlayerService;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

/**
 * Controller class for managing game engine operations.
 */
@Component
public class GameEngineController {

    /**
     * ContinentService for continent-related operations
     */
    private final ContinentService d_continentService;
    /**
     * Country service for country-related operations
     */
    private final CountryService d_countryService;
    /**
     * PlayerService for player-related operations
     */
    private final PlayerService d_playerService;
    /**
     * MapService for map-related operations
     */
    private final MapService d_mapService;
    /**
     * PhaseRepository for fetching and setting the current game phase
     */
    private final PhaseRepository d_phaseRepository;

    /**
     * Constructs a GameEngineController with the specified services.
     *
     * @param p_continentService The ContinentService to use.
     * @param p_countryService   The CountryService to use.
     * @param p_playerService    The PlayerService to use.
     * @param p_mapService       The MapService to use.
     * @param p_phaseRepository  The PhaseRepository to use.
     */
    public GameEngineController(ContinentService p_continentService, CountryService p_countryService, PlayerService p_playerService, MapService p_mapService, PhaseRepository p_phaseRepository) {
        this.d_continentService = p_continentService;
        this.d_countryService = p_countryService;
        this.d_playerService = p_playerService;
        this.d_mapService = p_mapService;
        this.d_phaseRepository = p_phaseRepository;
    }

    /**
     * Adds a continent to the game.
     *
     * @param p_continentDto The ContinentDto representing the continent to add.
     * @return A string indicating the result of the operation.
     */
    public String addContinent(ContinentDto p_continentDto) {
        if (Phase.MAP_EDITOR.equals(this.d_phaseRepository.getPhase())) {
            return d_continentService.add(p_continentDto);
        } else {
            return "Invalid Phase";
        }
    }

    /**
     * Deletes a continent from the game.
     *
     * @param p_continentId The ID of the continent to delete.
     * @return A string indicating the result of the operation.
     */
    public String deleteContinent(String p_continentId) {
        // Implementation goes here
        return null;
    }

    /**
     * Removes a country from the game.
     *
     * @param p_option The option specifying the country to remove.
     * @return A string indicating the result of the operation.
     */
    public String removeCountry(String p_option) {
        // Implementation goes here
        return null;
    }

    /**
     * Adds a country to the game.
     *
     * @param p_dto The CountryDto representing the country to add.
     * @return A string indicating the result of the operation.
     */
    public String addCountry(CountryDto p_dto) {
        d_countryService.add(p_dto);
        return "Country " + p_dto.getId() + " added";
    }

    /**
     * Adds a player to the game.
     *
     * @param p_playerDto The PlayerDto representing the player to add.
     * @return A string indicating the result of the operation.
     */
    public String addPlayer(PlayerDto p_playerDto) {
        String playerName = p_playerDto.getPlayerName();
        return d_playerService.add(p_playerDto);
    }

    /**
     * Removes a player from the game.
     *
     * @param p_playerName The name of the player to remove.
     * @return A string indicating the result of the operation.
     */
    public String removePlayer(String p_playerName) {
        return d_playerService.remove(p_playerName);
    }

    /**
     * Randomly assigns the countries to the players
     * @return the result of the operation
     * @throws NotFoundException when countries aren't found
     */
    public String assignCountries() throws NotFoundException {
        if (this.d_phaseRepository.getPhase() != Phase.STARTUP) {
            throw new InvalidCommandException("game not in startup phase");
        }

        this.d_playerService.assignCountries();
        this.d_phaseRepository.setPhase(Phase.GAME_LOOP);

        this.d_playerService.startGameLoop();
        return "";
    }

    /**
     * Deoplys a given number of armies into a specified country
     * @param countryId the id of the country
     * @param numOfReinforcements the number of reinforcement armies to deploy
     * @return the result of the operation
     */
    public String deploy(String countryId, int numOfReinforcements) {
        if (this.d_phaseRepository.getPhase() != Phase.GAME_LOOP) {
            throw new InvalidCommandException("game is not in game loop phase");
        }

        this.d_playerService.addDeployOrder(countryId, numOfReinforcements);
        return "";
    }

    /**
     * Method to go to the next phase, depending on current phase
     *
     * @return the phase after the change of phase
     */
    public String nextPhase() {

        Phase nextPhase = switch (d_phaseRepository.getPhase()) {
            case MAP_EDITOR -> Phase.STARTUP;
            case STARTUP -> Phase.GAME_PLAY;
            default -> Phase.GAME_LOOP;
        };

        this.d_phaseRepository.setPhase(nextPhase);

        return "Current phase is " + nextPhase;
    }
}
