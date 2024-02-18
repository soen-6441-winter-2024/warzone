package ca.concordia.app.warzone.service.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static int d_lastPlayerID = 0;
    private int d_playerID;
    private String d_playerName;
    private List<Country> d_countriesAssigned;

    public Player(){
        this.d_playerID = d_lastPlayerID + 1;
        this.d_countriesAssigned = new ArrayList<>();
    }

    public int get_playerID(){
        return d_playerID;
    }

    public void addCountry(Country country){
        d_countriesAssigned.add(country);
    }


    public String get_playerName(){
        return d_playerName;
    }

    public void set_playerName(String d_playerName){
        this.d_playerName = d_playerName;
    }

    public List<Country> get_countriesAssigned(){
        return d_countriesAssigned;
    }

    public void set_countriesAssigned(List<Country> d_countriesAssigned){
        this.d_countriesAssigned = d_countriesAssigned;
    }
}
