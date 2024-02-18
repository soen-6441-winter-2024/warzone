package ca.concordia.app.warzone.service.model;

public class DeployOrder extends Order {
    public DeployOrder(String player, String countryId, int number) {
        super(player);
        this.countryId = countryId;
        this.number = number;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private String countryId;
    private int number;
}
