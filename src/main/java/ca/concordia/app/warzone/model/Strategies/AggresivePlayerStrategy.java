package ca.concordia.app.warzone.model.Strategies;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.PlayerService;

import java.util.*;


/**
 * Represents the strategy for a Agressive computer player in the game.
 */
public class AggresivePlayerStrategy extends PlayerStrategy{
    private int d_currentRound;

    private int d_currentPlayerGivingOrder;
    private CountryService countryService;
    private PlayerService d_playerService;
    private final PhaseRepository d_phaseRepository;
    private List<List<String>> d_diplomacyList;

    /**
     * Constructs a AggresiveComputerPlayerStrategy with the specified parameters.
     *
     * @param d_player the player associated with this strategy
     * @param d_countriesAssigned the list of countries assigned to the player
     * @param p_currentRound the current round number
     * @param p_phaseRepository the phase repository
     * @param p_playerService the player service
     * @param p_diplomacyList the list of diplomacy contracts
     */
    public AggresivePlayerStrategy(Player d_player, List<Country> d_countriesAssigned, int p_currentRound, PhaseRepository p_phaseRepository, PlayerService p_playerService, List<List<String>> p_diplomacyList) {
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
    @Override
    public Country countryToAttackFrom() {
        List<Country> p_countriesassigned = d_player.getCountriesAssigned();
        int max = Integer.MIN_VALUE;
        Country maxTerritory = null;
        for(Country country : p_countriesassigned){
            int armiesCount = country.getArmiesCount();
            if(armiesCount > max){
                max = armiesCount;
                maxTerritory = country;
            }
        }

        return maxTerritory;
    }


    /**
     * Selects the country to be attacked.
     *
     * @return the country to be attacked
     */
    @Override
    public Country attackCountry(Country p_currentCountryToAttackFrom) {
        List<Country> p_neighbors = p_currentCountryToAttackFrom.getNeighbors();
        int min = Integer.MAX_VALUE;
        Country minNeighbor = null;



        for(Country country : p_neighbors){
            Optional<Player> optionalplayer = country.getPlayer();
            if(optionalplayer.isPresent()){
                Player player = optionalplayer.get();
                String playerId = player.getId();
                int armiesCount = country.getArmiesCount();
                if(armiesCount < min && !Objects.equals(playerId, d_player.getId())){
                    min = armiesCount;
                    minNeighbor = country;
                }

            }
        }

        if(minNeighbor == null){
            minNeighbor = p_currentCountryToAttackFrom;
        }

        return minNeighbor;
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
        Country currentCountryToAttackFrom = countryToAttackFrom();
        this.d_phaseRepository.getPhase().addDeployOrdersToPlayer(currentCountryToAttackFrom.getId(), armiestobedeployed, d_currentPlayerGivingOrder, d_currentRound);
        this.d_phaseRepository.getPhase().addAdvanceOrderToPlayer(currentCountryToAttackFrom.getId(), attackCountry(currentCountryToAttackFrom).getId(), fullForceArmy, d_currentPlayerGivingOrder, d_currentRound, d_diplomacyList);
        List<String> playerCards = d_player.getCards();
        Collections.shuffle(playerCards);
        for(String card : playerCards) {
            switch (card) {
                case "Airlift":
                    this.d_phaseRepository.getPhase().addAirliftOrderToPlayer(countryToAttackFrom().getId(), attackCountry(currentCountryToAttackFrom).getId(), fullForceArmy, d_currentPlayerGivingOrder, d_currentRound);
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
