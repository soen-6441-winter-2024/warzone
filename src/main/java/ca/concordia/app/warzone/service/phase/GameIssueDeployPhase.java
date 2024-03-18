package ca.concordia.app.warzone.service.phase;

import ca.concordia.app.warzone.service.PlayerService;

/**
 * Represents the game issue deploy phase of the game.
 */
public class GameIssueDeployPhase extends GamePhase {
    /**
     * The player service for adding deploy orders to players.
     */
    private final PlayerService d_playerService;

    /**
     * Constructs a new game issue deploy phase with the specified player service.
     *
     * @param p_playerService The player service.
     */
    public GameIssueDeployPhase(PlayerService p_playerService) {
        d_playerService = p_playerService;
    }

    /**
     * Adds deploy orders to the current player.
     *
     * @param countryId The ID of the country to deploy armies to.
     * @param numOfReinforcements The number of armies to deploy.
     * @param p_currentPlayerGivingOrder The ID of the current player giving the order.
     * @param p_currentRound The current round number.
     * @return A message indicating the success or failure of the operation.
     */
    @Override
    public String addDeployOrdersToPlayer(String countryId, int numOfReinforcements, int p_currentPlayerGivingOrder, int p_currentRound) {
        return this.d_playerService.addDeployOrderToCurrentPlayer(countryId, numOfReinforcements,
                p_currentPlayerGivingOrder, p_currentRound);
    }

    /**
     * Moves the game to the next phase.
     *
     * @return The next phase of the game.
     */
    @Override
    public Phase next() {
        return new GameIssueOrderPhase(d_playerService);
    }
}
