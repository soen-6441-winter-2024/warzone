package ca.concordia.app.warzone.console.commands;

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
