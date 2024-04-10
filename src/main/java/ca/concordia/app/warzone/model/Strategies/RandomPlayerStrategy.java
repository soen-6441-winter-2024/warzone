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
public class RandomPlayerStrategy extends PlayerStrategy {

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
     * @param p_currentRound the current round number
     * @param p_phaseRepository the phase repository
     * @param p_playerService the player service
     * @param p_diplomacyList the list of diplomacy contracts
     */
    public RandomPlayerStrategy(Player d_player, List<Country> d_countriesAssigned, int p_currentRound, PhaseRepository p_phaseRepository, PlayerService p_playerService, List<List<String>> p_diplomacyList) {
        super(d_player, d_countriesAssigned);
        this.d_currentRound = p_currentRound;
        this.d_phaseRepository = p_phaseRepository;
        this.d_playerService = p_playerService;
        this.d_diplomacyList = p_diplomacyList;
    }



    /**
     * Selects the country from which to launch an attack.
     *
     * @return the country from which to attack
     */
    public Country countryToAttackFrom(){
        Random rand = new Random();
        int targetCountry;
        targetCountry = rand.nextInt(d_player.getCountriesAssigned().size());
        Country countryToAttackFrom = d_player.getCountriesAssigned().get(targetCountry);
        if(countryToAttackFrom.getArmiesCount() == 0){
            return countryToAttackFrom();
        }
        else{
            return countryToAttackFrom;
        }
    }

    /**
     * Selects the country to be attacked.
     *
     * @return the country to be attacked
     */
    public Country attackCountry(Country p_currentCountryToAttackFrom){
        Random rand = new Random();
        int targetCountry;
        targetCountry = rand.nextInt(p_currentCountryToAttackFrom.getNeighbors().size());
        Country countryToAttack = d_player.getCountriesAssigned().get(targetCountry);
        Optional <Player> optionalplayer = countryToAttack.getPlayer();
        /*if(optionalplayer.isPresent()){
            Player player = optionalplayer.get();
            String playerId = player.getId();
            if(Objects.equals(playerId, d_player.getId())){
                attackCountry();
            }
            else {
                return countryToAttack;
            }
        }*/
        return countryToAttack;



    }

    /**
     * Creates orders based on the strategy.
     *
     * @return a string representing the created orders
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

        this.d_phaseRepository.getPhase().addAdvanceOrderToPlayer(countryToAttackFrom().getId(), attackCountry(currentCountryToAttackFrom).getId(), randomNumofArmies, d_currentPlayerGivingOrder, d_currentRound, d_diplomacyList);
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
