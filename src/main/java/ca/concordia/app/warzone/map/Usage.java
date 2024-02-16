package ca.concordia.app.warzone.map;

import javax.swing.*;
import java.io.File;

public record Usage() {
    /**
     * The Usage class is simply for showing example uses of GameMap class's methods
     * @param args
     */
    public static void main(String args[]) {
        GameMap map = new GameMap();

        map.loadMap("solar.map");
        map.showMap();

        // Examples: Editing a loaded map. Note: this does not make changes to the domination .map file
        map.addContinent("Africa", 834, 50);
        map.addContinent("North America", 945, 60);

        map.addCountry("Nigeria", 198989, 834, 1200);
        map.addCountry("USA", 247534, 945, 2000);

        map.addConnection(198989, 1);
        map.addConnection(198989, 2);

        map.showMap();
    }
}
