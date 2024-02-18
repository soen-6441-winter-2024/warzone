package ca.concordia.app.warzone.console.dto;

import java.util.ArrayList;
import java.util.List;
public class CountryDto {

    private String id;
    private String neighborId;
    private ContinentDto continent;
    private List<CountryDto> neighbors;
    private PlayerDto player;

    public CountryDto() {
        this.neighbors = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNeighborId() {
        return neighborId;
    }
    public void setNeighborId(String id) {
        this.neighborId = neighborId;
    }

    public ContinentDto getContinent()
    {
        return continent;
    }

    public void setContinent(ContinentDto continent)
    {
        this.continent = continent;
    }
    public List<CountryDto> getNeighbors()
    {
        return neighbors;
    }
    public void setNeighbors(List<CountryDto> neighbors)
    {
        this.neighbors = neighbors;
    }

    public PlayerDto getPlayer()
    {
        return player;
    }

    public void setPlayer(PlayerDto player)
    {
        this.player = player;
    }
}
