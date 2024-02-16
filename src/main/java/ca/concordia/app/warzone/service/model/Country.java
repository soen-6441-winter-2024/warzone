package ca.concordia.app.warzone.service.model;

import java.util.List;

public class Country {

    private String id;

    private Continent continent;

    private List<Country> neighbours;

    private Player player;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
}
