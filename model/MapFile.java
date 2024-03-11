package ca.concordia.app.warzone.model;
/**
 * Represents a Map File in the game.
 */
public class MapFile {
    private String d_FileName; // Data member for file Name

    /**
     * Default constructor
     */
    public MapFile() {

    }
    /**
     * Gets the file name of the map.
     *
     * @return The file name of the map.
     */
    public String getFileName() {
        return d_FileName;
    }

    /**
     * Sets the file name of the map.
     *
     * @param fileName The file name of the map.
     */
    public void setFileName(String fileName) {
        this.d_FileName = fileName;
    }
}
