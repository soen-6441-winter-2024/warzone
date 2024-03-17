package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.PlayerService;

public class GameStartupPhase extends GamePhase {
    private final PlayerService d_playerService;

    public GameStartupPhase(PlayerService p_playerService) {
        this.d_playerService = p_playerService;
    }

    @Override
    public Phase next() {
        return new GameIssueDeployPhase(d_playerService);
    }

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
