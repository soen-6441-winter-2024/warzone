package ca.concordia.app.warzone.map;

public class Country {
    private int id;
    private int numberOfArmies;
    private String name;
    private String owner = null; // should preferably be a player object
    private int continentId;

    public int getId() {
        return id;
    }

    public int getNumberOfArmies() {
        return numberOfArmies;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setNumberOfArmies(int armies) {
        this.numberOfArmies = armies;
    }

    /**
     * Constructs a country object
     * 
     * @param id
     * @param numberOfArmies
     * @param name
     */
    public Country(int id, int numberOfArmies, String name, int continentId) {
        this.id = id;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
        this.continentId = continentId;
    }
}