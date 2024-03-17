package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.service.PlayerService;

public class GameIssueDeployPhase extends GamePhase {
    private final PlayerService d_playerService;

    public GameIssueDeployPhase(PlayerService p_playerService) {
        d_playerService = p_playerService;
    }

    @Override
    public String addDeployOrdersToPlayer(String countryId, int numOfReinforcements, int p_currentPlayerGivingOrder, int p_currentRound) {
        return this.d_playerService.addDeployOrderToCurrentPlayer(countryId, numOfReinforcements,
                p_currentPlayerGivingOrder, p_currentRound);
    }

    @Override
    public Phase next() {
        return new GameIssueOrderPhase(d_playerService);
    }
}
