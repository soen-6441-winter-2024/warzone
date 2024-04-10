package ca.concordia.app.warzone.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a game for a particular map
 */
public class MapGameResult {
    /**
     * Return the map name
     * @return the map name
     */
    public String getD_mapName() {
        return d_mapName;
    }

    /**
     * Sets the map name
     * @param d_mapName the name of the map
     */
    public void setD_mapName(String d_mapName) {
        this.d_mapName = d_mapName;
    }

    /**
     * Returns the winners of the game
     * @return the winners of the game
     */
    public List<String> getD_gameWinners() {
        return d_gameWinners;
    }

    /**
     * Sets the game winners
     * @param d_gameWinners the winners of the game
     */
    public void setD_gameWinners(List<String> d_gameWinners) {
        this.d_gameWinners = d_gameWinners;
    }

    /**
     * The map name
     */
    String d_mapName;
    /**
     * The winners of the game
     */
    List<String> d_gameWinners;

    /**
     * Default constructor
     * @param p_mapName the map name
     */
    public MapGameResult(String p_mapName) {
        this.d_mapName = p_mapName;
        this.d_gameWinners = new ArrayList<>();
    }

    /**
     * Adds a result to the list of results
     * @param p_gameResult the game winner
     */
    public void addGameResult(String p_gameResult) {
        this.d_gameWinners.add(p_gameResult);
    }
}
