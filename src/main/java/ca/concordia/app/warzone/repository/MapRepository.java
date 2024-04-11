package ca.concordia.app.warzone.repository;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.model.MapFile;

import java.util.List;


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

    /**
     * Get a map given a path
     *
     * @param p_filePath file path where to get the map from
     * @return map as a list of string lines.
     */
    List<String> getMapAsString(String p_filePath);

    /**
     * Get a map given a path
     *
     * @param p_mapDto map dto containing the file path where to get the map from and the format
     * @return map containing countries and continents.
     */
    MapFile getMap(MapDto p_mapDto);
}
