package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.PlayerService;

import java.util.Collections;
import java.util.List;

public class BenevolentPlayerStrategy extends PlayerStrategy{
    private int d_currentRound;

    private int d_currentPlayerGivingOrder;
    private CountryService countryService;
    private PlayerService d_playerService;
    private final PhaseRepository d_phaseRepository;
    private List<List<String>> d_diplomacyList;
    public BenevolentPlayerStrategy(Player d_player, List<Country> d_countriesAssigned, int p_currentRound, PhaseRepository p_phaseRepository, PlayerService p_playerService, List<List<String>> p_diplomacyList) {
        super(d_player, d_countriesAssigned);
        this.d_currentRound = p_currentRound;
        this.d_phaseRepository = p_phaseRepository;
        this.d_playerService = p_playerService;
        this.d_diplomacyList = p_diplomacyList;
    }

    /**
     * @return
     */
    @Override
    public Country attackCountry() {
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
     * @return
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
     * @return
     */
    @Override
    public String createOrder() {
        int armiestobedeployed = this.d_player.getNumberOfReinforcements();
        int fullForceArmy = this.countryToAttackFrom().getArmiesCount();
        int halfForceArmy = fullForceArmy / 2;
        this.d_phaseRepository.getPhase().addDeployOrdersToPlayer(countryToAttackFrom().getId(), armiestobedeployed, d_currentPlayerGivingOrder, d_currentRound);
        this.d_phaseRepository.getPhase().addAdvanceOrderToPlayer(countryToAttackFrom().getId(), attackCountry().getId(), halfForceArmy, d_currentPlayerGivingOrder, d_currentRound, d_diplomacyList);
        List<String> playerCards = d_player.getCards();
        Collections.shuffle(playerCards);
        for(String card : playerCards) {
            switch (card) {
                case "Airlift":
                    this.d_phaseRepository.getPhase().addAirliftOrderToPlayer(countryToAttackFrom().getId(), attackCountry().getId(), halfForceArmy, d_currentPlayerGivingOrder, d_currentRound);
                    break;

                case "Blockade":
                    this.d_phaseRepository.getPhase().addBlockadeOrderToPlayer(attackCountry().getId(), d_currentPlayerGivingOrder, d_currentRound);
                    break;

                default:
                    break;
            }
        }


        return null;
    }


}
