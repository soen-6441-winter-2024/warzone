package ca.concordia.app.warzone.service.phase;

import java.util.List;

import ca.concordia.app.warzone.service.PlayerService;

/**
 * Represents the game issue order phase of the game.
 */
public class GameIssueOrderPhase extends GamePhase {
    /**
     * The player service for adding orders to players.
     */
    final PlayerService d_playerService;

    /**
     * Constructs a new game issue order phase with the specified player service.
     *
     * @param p_playerService The player service.
     */
    public GameIssueOrderPhase(PlayerService p_playerService) {
        d_playerService = p_playerService;
    }

    /**
     * Adds an advance order to the current player.
     *
     * @param countryNameFrom The name of the country to advance armies from.
     * @param countryNameTo The name of the country to advance armies to.
     * @param armiesQuantity The number of armies to advance.
     * @param p_currentPlayerGivingOrder The ID of the current player giving the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    @Override
    public String addAdvanceOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound, List<List<String>> p_diplomacyList) {
        return this.d_playerService.addAdvanceOrderToCurrentPlayer(countryNameFrom, countryNameTo, armiesQuantity, p_currentPlayerGivingOrder, p_currentRound, p_diplomacyList);
    }

    /**
     * Adds an airlift order to the current player.
     *
     * @param countryNameFrom The name of the country to airlift armies from.
     * @param countryNameTo The name of the country to airlift armies to.
     * @param armiesQuantity The number of armies to airlift.
     * @param p_currentPlayerGivingOrder The ID of the current player giving the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    @Override
    public String addAirliftOrderToPlayer(String countryNameFrom, String countryNameTo, int armiesQuantity, int p_currentPlayerGivingOrder, int p_currentRound) {
        return this.d_playerService.addAirliftOrderToCurrentPlayer(countryNameFrom, countryNameTo, armiesQuantity, p_currentPlayerGivingOrder, p_currentRound);
    }

    /**
     * Adds a blockade order to the current player.
     *
     * @param country The name of the country to blockade.
     * @param p_currentPlayerGivingOrder The ID of the current player giving the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    @Override
    public String addBlockadeOrderToPlayer(String country, int p_currentPlayerGivingOrder, int p_currentRound) {
        return this.d_playerService.addBlockadeOrderToCurrentPlayer(country, p_currentPlayerGivingOrder, p_currentRound);
    }
}
