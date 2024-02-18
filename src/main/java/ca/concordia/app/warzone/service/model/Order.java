package ca.concordia.app.warzone.service.model;

public abstract class Order {
    public Order(String player) {
        this.player = player;
    }

     private final String player;
}
