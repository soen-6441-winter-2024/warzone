package ca.concordia.app.warzone.console.dto;

/**
 * Represents a DTO (Data Transfer Object) for a map.
 */
public class MapDto {
    /**
     * Default constructor
     */
    public MapDto(){}

    /** The file name of the map. */
    private String d_FileName;

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
