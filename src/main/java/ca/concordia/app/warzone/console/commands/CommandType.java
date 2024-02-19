package ca.concordia.app.warzone.console.commands;

/**
 * Enum representing different types of commands.
 */
public enum CommandType {
    EDIT_CONTINENT("editcontinent"),
    EDIT_COUNTRY("editcountry"),
    EDIT_NEIGHBOR("editneighbor"),
    SHOW_MAP("showmap"),
    SAVE_MAP("savemap"),
    EDIT_MAP("editmap"),
    LOAD_MAP("loadmap"),
    ASSIGN_COUNTRIES("assigncountries"),
    GAME_PLAYER("gameplayer"),
    DEPLOY("deploy"),
    VALIDATE_MAP("validatemap"),
    NEXT_PHASE("nextphase")
    ;

    /** The text representation of the command type. */
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