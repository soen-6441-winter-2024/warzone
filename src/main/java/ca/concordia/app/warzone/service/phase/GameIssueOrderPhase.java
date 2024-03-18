package ca.concordia.app.warzone.service.phase;

import java.util.List;

import ca.concordia.app.warzone.service.PlayerService;

public class GameIssueOrderPhase extends GamePhase {
    final PlayerService d_playerService;

    public GameIssueOrderPhase(PlayerService p_playerService) {
        d_playerService = p_playerService;
    }

    @Override
    public String addRegularOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound, List<List<String>> p_diplomacyList) {
        return this.d_playerService.addAdvanceOrderToCurrentPlayer(countryNameFrom, countryNameTo, armiesQuantity, p_currentPlayerGivingOrder, p_currentRound, p_diplomacyList);
    }
}
