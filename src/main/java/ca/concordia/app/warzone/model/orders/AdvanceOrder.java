package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;

import java.util.Optional;

/**
 * Represents advance order
 *
 */
public class AdvanceOrder extends Order {

    /**
     * get origin country
     * @return origin country id
     */
    public String getD_countryFrom() {
        return d_countryFrom;
    }

    /**
     * set the origin country
     *
     * @param d_countryFrom origin country id
     */
    public void setD_countryFrom(String d_countryFrom) {
        this.d_countryFrom = d_countryFrom;
    }

    /**
     * get destination country
     * @return destination country id
     */
    public String getD_countryTo() {
        return d_countryTo;
    }

    /**
     * set the destination country
     *
     * @param d_countryTo destination country id
     */
    public void setD_countryTo(String d_countryTo) {
        this.d_countryTo = d_countryTo;
    }

    /**
     * get number of armies
     *
     * @return number of armies
     */
    public int getD_number() {
        return d_number;
    }

    /**
     * set the number of armies
     *
     * @param d_number number of armies
     */
    public void setD_number(int d_number) {
        this.d_number = d_number;
    }

    private String d_countryFrom;
    private String d_countryTo;
    private int d_number;

    final private CountryService d_countryService;

    /**
     * Creates an advance order
     *
     * @param p_player player id
     * @param p_countryFrom origin country id
     * @param p_countryTo destination country id
     * @param p_number numer of armies
     * @param p_countryService country service
     *
     */
    public AdvanceOrder(String p_player, String p_countryFrom, String p_countryTo, int p_number, CountryService p_countryService) {
        super(p_player);
        this.d_countryFrom = p_countryFrom;
        this.d_countryTo = p_countryTo;
        this.d_number = p_number;
        d_countryService = p_countryService;
    }

    @Override
    public void execute() {
        System.out.println("Trying to execute advance order...");
        Optional<Country> countryFromOptional = this.d_countryService.findCountryById(d_countryFrom);
        Optional<Country> countryToOptional = this.d_countryService.findCountryById(d_countryTo);

        if(countryFromOptional.isEmpty()){
            return;
        }

        if(countryToOptional.isEmpty()){
            return;
        }

        Country countryTo = countryToOptional.get();
        Country countryFrom = countryFromOptional.get();

        if(countryFrom.getPlayer().isEmpty()) {
            System.out.println(countryFrom.getId() + " has no owner. Aborting advance order");
            return;
        }

        try {
            if(!this.d_countryService.areNeighbors(this.d_countryFrom, this.d_countryTo)) {
                System.out.println(countryFrom.getId() + " and " + countryTo.getId() + " are not neighbors. Aborting advance order.");
                return;
            }
        } catch (NotFoundException e) {
            return;
        }


        Player countryFromOwner = countryFrom.getPlayer().get();

        if(!countryFromOwner.getPlayerName().equals(this.player)) {
            System.out.println(this.player + " does not own " + countryFrom.getId() + ". Aborting advance order.");
            return;
        }

        if(countryFrom.getArmiesCount() < this.d_number) {
            System.out.println(countryFrom.getId() + " does not have enough armies. " + ". Aborting advance order.");
            return;
        }

        this.d_countryService.removeArmiesFromCountry(this.d_countryFrom, this.d_number);

        Optional<Player> countryToOwnerOptional = countryTo.getPlayer();

        // If the countryTo has no owner, then we just move the armies to the country
        if(countryToOwnerOptional.isEmpty()) {
            System.out.println("Advanced " + this.d_number + " armies from " + this.d_countryFrom + " to " + this.d_countryTo );
            countryTo.setArmiesCount(countryTo.getArmiesCount() + this.d_number);
            countryTo.setPlayer(Optional.of(countryFromOwner));
            countryFromOwner.addCard("airlift_card");
            return;
        }

        Player countryToOwner = countryToOwnerOptional.get();

        // If the player is the owner, we just move the armies to the country
        if(countryToOwner.ownsCountry(this.d_countryTo)) {
            System.out.println("Advanced " + this.d_number + " armies from " + this.d_countryFrom + " to " + this.d_countryTo );
            countryTo.setArmiesCount(countryTo.getArmiesCount() + this.d_number);
            return;
        }

        // Otherwise, there's a battle
        int attackingArmies = this.d_number;
        int defendingArmies = countryTo.getArmiesCount();


        while (true) {
            double randomNumberForAttacking = Math.random();
            if(randomNumberForAttacking <= 0.6) {
                attackingArmies--;
            }

            double randomNumberForDefending = Math.random();
            if(randomNumberForDefending <= 0.7) {
                defendingArmies--;
            }

            if(attackingArmies == 0 || defendingArmies == 0) {
                break;
            }
        }

        // Defending won
        if(defendingArmies > attackingArmies) {
            System.out.println("Failed to advance " + this.d_number + "armies from " + this.d_countryFrom + " to " + this.d_countryTo + ". Defending armies won.");
            this.d_countryService.setArmiesCountToCountry(this.d_countryTo, defendingArmies);

            return;
        }

        System.out.println("Advanced " + this.d_number + " armies from " + this.d_countryFrom + " to " + this.d_countryTo + ". Attacking armies won armies won.");
        // Attacking won, the owner of the country changes
        this.d_countryService.setArmiesCountToCountry(this.d_countryTo, attackingArmies);
        countryTo.setPlayer(Optional.of(countryFromOwner));
        countryFromOwner.addCard("airlift_card");
    }
}
