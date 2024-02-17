package ca.concordia.app.warzone.map;


public class Continent {
    private String name;
    private int id;
    private int bonusArmies;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }

    public Continent(String name, int id, int bonusArmies) {
        this.name = name;
        this.id = id;
        this.bonusArmies = bonusArmies;
    }
}