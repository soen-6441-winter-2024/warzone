package ca.concordia.app.warzone.Player;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int d_PlayerID;

    private String d_PlayerName;
    private List<String> d_CountriesAssigned;

    public int getPlayerID(){
        return d_PlayerID;
    }

    private static int l_lastplayerID = 0;


    /**
     *
     * @param playerName
     */
    public void setPlayer(String playerName){
        this.d_PlayerName = playerName;
        this.d_PlayerID = l_lastplayerID + 1;
        l_lastplayerID++;
        d_CountriesAssigned = new ArrayList<>();

    }

    /**
     *
     * @param Country
     */
    public void addCountry(String Country){
        d_CountriesAssigned.add(Country);
    }


    /**
     *
     * @return the name of the player
     */
    public String getPlayerName(){
        return d_PlayerName;
    }

    /**
     *
     * @return countries assigned to the player
     */
    public List<String> getCountriesAssigned(){
        return d_CountriesAssigned;
    }

}

