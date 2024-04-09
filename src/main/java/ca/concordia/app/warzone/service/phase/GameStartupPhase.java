package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.PlayerService;

/**
 * Represents the game startup phase of the game.
 */
public class GameStartupPhase extends GamePhase {
    /**
     * The player service for adding and removing players.
     */
    private final PlayerService d_playerService;

    /**
     * Constructs a new game startup phase with the specified player service.
     *
     * @param p_playerService The player service.
     */
    public GameStartupPhase(PlayerService p_playerService) {
        this.d_playerService = p_playerService;
    }

    @Override
    public Phase next() {
        return new GameIssueDeployPhase(d_playerService);
    }

    /**
     * Returns a message indicating that editing the map is not allowed in this phase.
     *
     * @param p_mapDto The map DTO.
     * @return A message indicating that editing the map is not allowed in this phase.
     */
    public String editMap(MapDto p_mapDto) {
        return "Invalid phase";
    }

    @Override
    public String addPlayer(PlayerDto p_playerDto) {
        return this.d_playerService.add(p_playerDto);
    }

    @Override
    public String removePlayer(String p_playerName) {
        return this.d_playerService.remove(p_playerName);
    }

    @Override
    public String assignCountries() {
        this.d_playerService.assignCountries();
        return "Countries assigned";
    }
}
