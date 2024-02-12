package ca.concordia.app.warzone.console.commands;

public enum CommandType {
    EDIT_CONTINENT("editcontinent"),
    EDIT_COUNTRY("editcountry"),
    EDIT_NEIGHBOR("editneighbor"),
    SHOW_MAP("showmap"),
    SAVE_MAP("savemap"),
    EDIT_MAP("editmap"),
    VALIDATE_MAP("validatemap")
    ;

    private final String text;

    CommandType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    }
