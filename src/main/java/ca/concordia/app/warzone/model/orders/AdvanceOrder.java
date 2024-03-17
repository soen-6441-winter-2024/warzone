package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AdvanceOrder extends Order {
    public String getD_countryFrom() {
        return d_countryFrom;
    }

    public void setD_countryFrom(String d_countryFrom) {
        this.d_countryFrom = d_countryFrom;
    }

    public String getD_countryTo() {
        return d_countryTo;
    }

    public void setD_countryTo(String d_countryTo) {
        this.d_countryTo = d_countryTo;
    }

    public int getD_number() {
        return d_number;
    }

    public void setD_number(int d_number) {
        this.d_number = d_number;
    }

    private String d_countryFrom;
    private String d_countryTo;
    private int d_number;

    final private CountryService d_countryService;



    public AdvanceOrder(String p_player, String p_countryFrom, String p_countryTo, int p_number, CountryService p_countryService) {
        super(p_player);
        this.d_countryFrom = p_countryFrom;
        this.d_countryTo = p_countryTo;
        this.d_number = p_number;
        d_countryService = p_countryService;
    }


    @Override
    public void execute() throws NotFoundException {
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
            return;
        }

        Player countryFromOwner = countryFrom.getPlayer().get();

        if(!countryFromOwner.getPlayerName().equals(this.player)) {
            return;
        }

        if(countryFrom.getArmiesCount() < this.d_number) {
            return;
        }

        this.d_countryService.removeArmiesFromCountry(this.d_countryFrom, this.d_number);

        Optional<Player> countryToOwnerOptional = countryTo.getPlayer();

        // If the countryTo has no owner, then we just move the armies to the country
        if(countryToOwnerOptional.isEmpty()) {
            this.d_countryService.addArmiesToCountry(this.d_countryTo,  this.d_number);
            return;
        }

        Player countryToOwner = countryToOwnerOptional.get();

        // If the player is the owner, we just move the armies to the country
        if(countryToOwner.ownsCountry(this.d_countryTo)) {
            this.d_countryService.addArmiesToCountry(this.d_countryTo,  this.d_number);
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
            this.d_countryService.setArmiesCountToCountry(this.d_countryTo, defendingArmies);
            return;
        }

        // Attacking won, the owner of the country changes
        this.d_countryService.setArmiesCountToCountry(this.d_countryTo, attackingArmies);
        countryTo.setPlayer(Optional.of(countryFromOwner));
    }
}
