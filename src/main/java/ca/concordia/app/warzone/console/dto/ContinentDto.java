package ca.concordia.app.warzone.console.dto;

/**
 * Represents a DTO (Data Transfer Object) for a continent.
 */
public class ContinentDto {

    /**
     * Default constructor
     */
    public ContinentDto() {}

    /** The ID of the continent. */
    private String d_Id;

    /** The value/name of the continent. */
    private String d_Value;

    /**
     * Gets the ID of the continent.
     *
     * @return The ID of the continent.
     */
    public String getId() {
        return d_Id;
    }

    /**
     * Sets the ID of the continent.
     *
     * @param id The ID of the continent.
     */
    public void setId(String id) {
        this.d_Id = id;
    }

    /**
     * Gets the value/name of the continent.
     *
     * @return The value/name of the continent.
     */
    public String getValue() {
        return d_Value;
    }

    /**
     * Sets the value/name of the continent.
     *
     * @param value The value/name of the continent.
     */
    public void setValue(String value) {
        this.d_Value = value;
    }

}
