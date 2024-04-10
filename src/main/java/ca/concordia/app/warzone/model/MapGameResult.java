package ca.concordia.app.warzone.model;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MapGameResult {
    public String getD_mapName() {
        return d_mapName;
    }

    public void setD_mapName(String d_mapName) {
        this.d_mapName = d_mapName;
    }

    public List<String> getD_gameWinners() {
        return d_gameWinners;
    }

    public void setD_gameWinners(List<String> d_gameWinners) {
        this.d_gameWinners = d_gameWinners;
    }

    String d_mapName;
    List<String> d_gameWinners;

    public MapGameResult(String p_mapName) {
        this.d_mapName = p_mapName;
        this.d_gameWinners = new ArrayList<>();
    }

    public void addGameResult(String p_gameResult) {
        this.d_gameWinners.add(p_gameResult);
    }
}
