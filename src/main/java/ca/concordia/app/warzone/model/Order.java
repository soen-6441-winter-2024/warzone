package ca.concordia.app.warzone.model;

/**
 * Represents an abstract order in the game, issued by a player.
 */
public abstract class Order {

    /**
     * The player who issued the order.
     */
    private final String player;

    /**
     * Constructs an Order object with the specified player.
     *
     * @param player The player issuing the order.
     */
    public Order(String player) {
        this.player = player;
    }

    /**
     * Retrieves the player who issued the order.
     *
     * @return The player who issued the order.
     */
    public String getPlayer() {
        return player;
    }
}

