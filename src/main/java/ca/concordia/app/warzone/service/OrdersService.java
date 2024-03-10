package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.model.DeployOrder;
import ca.concordia.app.warzone.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    List<List<Order>> d_orders;
    private int d_currentRound;

    private final CountryService d_countryService;



    public OrdersService(CountryService countryService) {
        this.d_countryService = countryService;
    }

    public void setOrders(List<List<Order>> orders){
        this.d_orders = orders;
    }

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
            String countryId = ((DeployOrder) p_order).getCountryId();
            int number = ((DeployOrder) p_order).getNumber();
            // Add the reinforcements to the country
            System.out.println("Adding " + number + " armies to country " + countryId);
            this.d_countryService.addArmiesToCountry(countryId, number);
        }
    }
}
