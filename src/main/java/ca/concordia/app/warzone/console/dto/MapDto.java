package ca.concordia.app.warzone.console.dto;

import ca.concordia.app.warzone.model.MapFileFormat;

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
     *  format of the file
     */
    private MapFileFormat format =  MapFileFormat.DEFAULT;

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

    /**
     * Get the format of the file
     *
     * @return format of the file
     */
    public MapFileFormat getFormat() {
        return format;
    }

    /**
     * Set the format of the file
     *
     * @param format format of the file
     */
    public void setFormat(MapFileFormat format) {
        this.format = format;
    }
}
