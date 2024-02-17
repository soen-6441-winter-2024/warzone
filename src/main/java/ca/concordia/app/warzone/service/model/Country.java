package ca.concordia.app.warzone.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Import Optional
public class Country {

    private String id;
    private Continent continent;
    private List<Country> neighbors;
    private Optional<Player> player;

    public Country() {
        this.neighbors = new ArrayList<>();
        this.player = Optional.empty(); // Initialize as empty
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Continent getContinent()
    {
        return continent;
    }

    public void setContinent(Continent continent)
    {
        this.continent = continent;
    }
    public List<Country> getNeighbors()
    {
        return neighbors;
    }
    public void setNeighbors(List<Country> neighbors)
    {
        this.neighbors = neighbors;
    }
    public Optional<Player> getPlayer()
    {
        return player;
    }

    public void setPlayer(Optional<Player> player)
    {
        this.player = player;
    }
}
