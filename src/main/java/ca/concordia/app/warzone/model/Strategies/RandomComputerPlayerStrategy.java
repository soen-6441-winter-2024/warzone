package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.orders.AdvanceOrder;
import ca.concordia.app.warzone.model.orders.AirliftOrder;
import ca.concordia.app.warzone.model.orders.DeployOrder;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.PlayerService;

import java.util.*;

/**
 * Represents the strategy for a random computer player in the game.
 */
public class RandomComputerPlayerStrategy extends ComputerStrategy {

    private int d_currentRound;
    private int d_currentPlayerGivingOrder;
    private CountryService countryService;
    private PlayerService d_playerService;
    private final PhaseRepository d_phaseRepository;
    private List<List<String>> d_diplomacyList;

    /**
     * Constructs a RandomComputerPlayerStrategy with the specified parameters.
     *
     * @param d_player the player associated with this strategy
     * @param d_countriesAssigned the list of countries assigned to the player
     * @param p_currentRound the current round of the game
     * @param p_phaseRepository the phase repository
     * @param p_playerService the player service
     * @param p_diplomacyList the list of diplomacy agreements
     */
    public RandomComputerPlayerStrategy(Player d_player, List<Country> d_countriesAssigned, int p_currentRound, PhaseRepository p_phaseRepository, PlayerService p_playerService, List<List<String>> p_diplomacyList, int p_currentPlayerGivingOrder) {
        super(d_player, d_countriesAssigned);
        this.d_currentRound = p_currentRound;
        this.d_phaseRepository = p_phaseRepository;
        this.d_playerService = p_playerService;
        this.d_diplomacyList = p_diplomacyList;
        this.d_currentPlayerGivingOrder = p_currentPlayerGivingOrder;
    }

    /**
     * Selects a random country from which to attack.
     *
     * @return the randomly selected country to attack from
     */
    public Country countryToAttackFrom() {
        Random rand = new Random();
        int targetCountry;
        targetCountry = rand.nextInt(d_player.getCountriesAssigned().size());
        Country countryToAttackFrom = d_player.getCountriesAssigned().get(targetCountry);

        return countryToAttackFrom;

    }

    /**
     * Selects a random neighboring country to attack.
     * @param p_currentCountryToAttackFrom the current country to attack from
     * @return the randomly selected neighboring country to attack
     */
    public Country attackCountry(Country p_currentCountryToAttackFrom){
        // country has no neighbor
        if(p_currentCountryToAttackFrom.getNeighbors().size() == 0) {
            return null;
        }

        Random rand = new Random();
        int targetCountry;
        targetCountry = rand.nextInt(p_currentCountryToAttackFrom.getNeighbors().size());
        Country countryToAttack = d_player.getCountriesAssigned().get(targetCountry);

        return countryToAttack;
    }

    /**
     * Creates random orders for the random computer player strategy.
     *
     * @return null (not applicable for random computer player strategy)
     */
    @Override
    public String createOrder() {
        Random rand = new Random();
        int armiesdeployed = 0;
        int randomNumofArmies = rand.nextInt(d_player.getNumberOfReinforcements());
        Country currentCountryToAttackFrom = countryToAttackFrom();
        while (armiesdeployed < this.d_player.getNumberOfReinforcements()){
            int numOfArmiesToDeploy = rand.nextInt(d_player.getNumberOfReinforcements());
            int posOfCountryToDeployTo = rand.nextInt(d_player.getCountriesAssigned().size());
            String countryToDeployTo = d_player.getCountriesAssigned().get(posOfCountryToDeployTo).getId();
            armiesdeployed += numOfArmiesToDeploy;
            this.d_phaseRepository.getPhase().addDeployOrdersToPlayer(countryToDeployTo, numOfArmiesToDeploy, d_currentPlayerGivingOrder, d_currentRound);
        }

        // Countries has no neighbors. Cannot attack
        if(!(currentCountryToAttackFrom.getNeighbors() == null || currentCountryToAttackFrom.getNeighbors().size() == 0)) {
            this.d_phaseRepository.getPhase().addAdvanceOrderToPlayer(countryToAttackFrom().getId(), attackCountry(currentCountryToAttackFrom).getId(), randomNumofArmies, d_currentPlayerGivingOrder, d_currentRound, d_diplomacyList);
        }


        List<String> playerCards = d_player.getCards();
        Collections.shuffle(playerCards);
        for(String card : playerCards) {
            switch (card) {
                case "Airlift":
                    this.d_phaseRepository.getPhase().addAirliftOrderToPlayer(countryToAttackFrom().getId(), attackCountry(currentCountryToAttackFrom).getId(), randomNumofArmies, d_currentPlayerGivingOrder, d_currentRound);
                    break;

                case "Blockade":
                    this.d_phaseRepository.getPhase().addBlockadeOrderToPlayer(attackCountry(currentCountryToAttackFrom).getId(), d_currentPlayerGivingOrder, d_currentRound);
                    break;

                case "Bomb":
                    this.d_playerService.addBombOrderToCurrentPlayer(attackCountry(currentCountryToAttackFrom).getId(), d_currentPlayerGivingOrder, d_currentRound);
                    break;
                default:
                    break;
            }
        }
        return null;
    }
}