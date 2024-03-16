package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.model.DeployOrder;
import ca.concordia.app.warzone.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service class that manages orders in a Warzone game.
 * This class provides methods for executing orders.
 */
@Service
public class OrdersService {
    List<List<Order>> d_orders;
    private int d_currentRound;

    private final CountryService d_countryService;


    /**
     * Constructor for OrdersService class
     * @param countryService the CountryService to be used
     */
    public OrdersService(CountryService countryService) {
        this.d_countryService = countryService;
    }

    /**
     * sets orders
     * @param orders, orders
     */
    public void setOrders(List<List<Order>> orders){
        this.d_orders = orders;
    }

    /**
     * Sets the current round
     * @param currentRound, the current round
     */
    public void setCurrentRound(int currentRound){
        this.d_currentRound = currentRound;
    }

    /**
     * Executes orders for the current round.
     */
    public void executeOrders() {
        List<Order> roundOrders = d_orders.get(d_currentRound);
        for (Order order : roundOrders) {
            executeOrder(order);
        }
    }

    /**
     * Executes a specific order.
     *
     * @param p_order the order to execute
     */
    private void executeOrder(Order p_order) {
        if (p_order instanceof DeployOrder) {
            ((DeployOrder)p_order).execute(p_order, d_countryService);
        }
    }
}
