package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.PlayerService;

import java.util.Collections;
import java.util.List;

/**
 * Represents the strategy for a benevolent computer player in the game.
 */
public class BenevolentComputerPlayerStrategy extends ComputerStrategy{
    /**
     * Current round number.
     */
    private int d_currentRound;

    /**
     * Index of the current player giving order.
     */
    private int d_currentPlayerGivingOrder;

    /**
     * Service for country operations.
     */
    private CountryService countryService;

    /**
     * Service for player operations.
     */
    private PlayerService d_playerService;

    /**
     * Repository for phase operations.
     */
    private final PhaseRepository d_phaseRepository;

    /**
     * List of diplomacy contracts.
     */
    private List<List<String>> d_diplomacyList;
    private CountryService d_countryService;

    /**
     * Constructs a BenevolentComputerPlayerStrategy with the specified parameters.
     *
     * @param d_player the player associated with this strategy
     * @param d_countriesAssigned the list of countries assigned to the player
     * @param p_currentRound the current round number
     * @param p_phaseRepository the phase repository
     * @param p_playerService the player service
     * @param p_diplomacyList the list of diplomacy contracts
     */
    public BenevolentComputerPlayerStrategy(Player d_player, List<Country> d_countriesAssigned, int p_currentRound, PhaseRepository p_phaseRepository, PlayerService p_playerService, List<List<String>> p_diplomacyList, CountryService p_countryService, int p_currentPlayerGivingOrder) {
        super(d_player, d_countriesAssigned);
        this.d_currentRound = p_currentRound;
        this.d_phaseRepository = p_phaseRepository;
        this.d_playerService = p_playerService;
        this.d_diplomacyList = p_diplomacyList;
        this.d_countryService = p_countryService;
        this.d_currentPlayerGivingOrder = p_currentPlayerGivingOrder;
    }

    /**
     * Selects the country to be attacked.
     * @param p_currentCountryToAttackFrom the current country to attack from
     * @return the country to be attacked
     */
    @Override
    public Country attackCountry(Country p_currentCountryToAttackFrom) {
        List<Country> p_countriesassigned = d_player.getCountriesAssigned();
        int min = Integer.MAX_VALUE;
        Country minTerritory = null;
        for(Country country : p_countriesassigned){
            int armiesCount = country.getArmiesCount();
            if(armiesCount < min){
                min = armiesCount;
                minTerritory = country;
            }
        }

        return minTerritory;
    }

    /**
     * Selects the country from which to launch an attack.
     *
     * @return the country from which to attack
     */
    @Override
    public Country countryToAttackFrom() {
        List<Country> p_countriesassigned = d_player.getCountriesAssigned();
        int min = Integer.MAX_VALUE;
        Country minTerritory = null;
        for(Country country : p_countriesassigned){
            int armiesCount = country.getArmiesCount();
            if(armiesCount < min){
                min = armiesCount;
                minTerritory = country;
            }
        }

        return minTerritory;
    }

    /**
     * Creates orders based on the strategy.
     *
     * @return a string representing the created orders
     */
    @Override
    public String createOrder() {
        int armiestobedeployed = this.d_player.getNumberOfReinforcements();
        int fullForceArmy = this.countryToAttackFrom().getArmiesCount();
        int halfForceArmy = fullForceArmy / 2;
        Country currentCountryToAttackFrom = countryToAttackFrom();
        this.d_phaseRepository.getPhase().addDeployOrdersToPlayer(countryToAttackFrom().getId(), armiestobedeployed, d_currentPlayerGivingOrder, d_currentRound);
        this.d_phaseRepository.getPhase().addAdvanceOrderToPlayer(countryToAttackFrom().getId(), attackCountry(currentCountryToAttackFrom).getId(), halfForceArmy, d_currentPlayerGivingOrder, d_currentRound, d_diplomacyList);
        List<String> playerCards = d_player.getCards();
        Collections.shuffle(playerCards);
        for(String card : playerCards) {
            switch (card) {
                case "Airlift":
                    this.d_phaseRepository.getPhase().addAirliftOrderToPlayer(countryToAttackFrom().getId(), attackCountry(currentCountryToAttackFrom).getId(), halfForceArmy, d_currentPlayerGivingOrder, d_currentRound);
                    break;

                case "Blockade":
                    this.d_phaseRepository.getPhase().addBlockadeOrderToPlayer(attackCountry(currentCountryToAttackFrom).getId(), d_currentPlayerGivingOrder, d_currentRound);
                    break;

                default:
                    break;
            }
        }


        return null;
    }

}