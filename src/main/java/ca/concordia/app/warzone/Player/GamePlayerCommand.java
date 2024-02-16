package ca.concordia.app.warzone.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * GamePlayerCommand class represents a class used to execute player related commands
 */

public class GamePlayerCommand implements PlayerCommandHandler{

    //List of players
    private List<Player> d_ListofPlayers = new ArrayList<>();

    /**
     *
     * @param d_ListofPlayers the list of players
     * @return
     */
    public List<Player> getListofPlayers(List<Player> d_ListofPlayers){
        return this.d_ListofPlayers;
    }

    public  GamePlayerCommand(List<Player> d_ListofPlayers) {
        this.d_ListofPlayers = d_ListofPlayers;
    }

    /**
     * Adds player to the list of players
     * @param p_PlayerName
     */
    @Override
    public void addPlayer(String p_PlayerName){
        Player newPlayer = new Player();
        newPlayer.setPlayer(p_PlayerName);
        d_ListofPlayers.add(newPlayer);
    }

    /**
     * Removes a player from the list of players
     *
     * @param p_PlayerName name of the player to remove
     */
    @Override
    public void removePlayer(String p_PlayerName){
        boolean l_PlayerExists = false;

        //Find and remove player if player exists
        for (Player l_player : d_ListofPlayers) {
            if ((p_PlayerName).equals(l_player.getPlayerName())) {
                d_ListofPlayers.remove(l_player);
                l_PlayerExists = true;
            }
        }
        //print error message if player isnt found
        if (l_PlayerExists) {
            System.out.println("Player does not exist");
        }
    }

    /**
     * Lists all players
     */
    @Override
    public void Listplayers() {
        System.out.println("List of players ");
        for(Player l_player : d_ListofPlayers){
            System.out.println(l_player.getPlayerName() + " " + l_player.getPlayerID());
        }
    }

}



