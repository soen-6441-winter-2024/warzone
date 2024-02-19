package ca.concordia.app.warzone.console.commands;

/**
 * An enum representing different types of commands in the game.
 */
public enum CommandType {
    /**
     * Command to edit continents.
     */
    EDIT_CONTINENT("editcontinent"),

    /**
     * Command to edit countries.
     */
    EDIT_COUNTRY("editcountry"),

    /**
     * Command to edit neighbors.
     */
    EDIT_NEIGHBOR("editneighbor"),

    /**
     * Command to show the map.
     */
    SHOW_MAP("showmap"),

    /**
     * Command to save the map.
     */
    SAVE_MAP("savemap"),

    /**
     * Command to edit the map.
     */
    EDIT_MAP("editmap"),

    /**
     * Command to load a map.
     */
    LOAD_MAP("loadmap"),

    /**
     * Command to assign countries to players.
     */
    ASSIGN_COUNTRIES("assigncountries"),

    /**
     * Command related to game players.
     */
    GAME_PLAYER("gameplayer"),

    /**
     * Command to deploy armies.
     */
    DEPLOY("deploy"),

    /**
     * Command to validate the map.
     */
    VALIDATE_MAP("validatemap"),

    /**
     * Command to move to the next phase.
     */
    NEXT_PHASE("nextphase");

    /**
     * The text representation of the command type.
     */
    private final String d_Text;

    /**
     * Constructs a CommandType enum with the given text.
     *
     * @param text The text representation of the command type.
     */
    CommandType(final String text) {
        this.d_Text = text;
    }

    /**
     * Returns the text representation of the command type.
     *
     * @return The text representation of the command type.
     */
    @Override
    public String toString() {
        return d_Text;
    }
}