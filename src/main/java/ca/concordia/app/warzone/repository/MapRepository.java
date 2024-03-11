package ca.concordia.app.warzone.repository;
import ca.concordia.app.warzone.model.MapFile;


/**
 * Interface for accessing and managing maps.
 */
public interface MapRepository {

    /**
     * Saves a map as a file.
     *
     * @param p_map the map file to save
     * @return a message indicating whether the map was saved successfully or not
     */
    String saveMap(MapFile p_map);
}
