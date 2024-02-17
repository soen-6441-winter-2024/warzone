package ca.concordia.app.warzone.console.dto;

import java.util.List;

public class PlayerDto {
    private String d_playerName;
    private List<String> d_countriesAssigned;

    public String get_playerName(){
        return d_playerName;
    }

    public void setD_playerName(String d_playerName){
        this.d_playerName = d_playerName;
    }

    public List<String> get_countriesAssigned(){
        return d_countriesAssigned;
    }

    public void set_countriesAssigned(List<String> d_countriesAssigned){
        this.d_countriesAssigned = d_countriesAssigned;
    }

}
