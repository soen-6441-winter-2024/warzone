package ca.concordia.app.warzone.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class AssignCountries {

    /**
     * retrieves the list of countries in the map
     * @return a shuffled list of the countries
     */

    public List<String> sampleCountryList(){
        //TODO get list of actual countries on map
        List<String> countries = new ArrayList<>();
        Collections.shuffle(countries);
        return countries;
    }


    /**
     * Assigns countries to the players
     *
     * @param d_ListofPlayers the list of players
     */
    public void assignCountries(List<Player> d_ListofPlayers){
        List<String> countries = sampleCountryList();
        int totalPlayers = d_ListofPlayers.size();
        int minCountriesPerPlayer = countries.size() / totalPlayers;
        int remainingCountries = countries.size() % totalPlayers;
        int i = 0;

        //Distribute the countries evenly among players
        for(Player l_player : d_ListofPlayers){
            for(int j = 0; j < remainingCountries; j++){
                l_player.addCountry(countries.get(i));
                System.out.println(l_player.getPlayerName() + " was assigned "+ countries.get(i));
                i++;

            }
        }

        //distribute remaining countries
        for(int j = 0; j < remainingCountries; j++){
            Player l_player = d_ListofPlayers.get(j);
            l_player.addCountry(countries.get(i));
            System.out.println(l_player.getPlayerName() + " was assigned " + countries.get(i));
            i++;

        }

    }

    /**
     * Lists all players with their now assigned countries
     * @param d_ListofPlayers
     */

    public void Listplayers(List<Player> d_ListofPlayers) {
        System.out.println("List of players ");
        for(Player l_player : d_ListofPlayers){
            System.out.println(l_player.getPlayerName() + " " + l_player.getPlayerID() + " " + l_player.getCountriesAssigned());
        }
    }
}
