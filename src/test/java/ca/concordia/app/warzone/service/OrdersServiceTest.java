package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.orders.DeployOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrdersServiceTest {

    private List<List<Order>> d_orders;

    @Mock
    private CountryService countryService;

    @Mock
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeOrders() {
        List<Order> roundOrders = new ArrayList<>();
        DeployOrder order1 = new DeployOrder("Player 1", "country1", 5, countryService);
        DeployOrder order2 = new DeployOrder("Player 2", "country2", 2, countryService);
        roundOrders.add(order2);
        roundOrders.add(order1);

        List<List<Order>> ordersList = new ArrayList<>();
        ordersList.add(roundOrders);

        for (Order order : ordersList.get(0)) {
            order.execute();
        }

        verify(countryService, times(1)).addArmiesToCountry(order1.getCountryId(), order1.getNumber());
        verify(countryService, times(1)).addArmiesToCountry(order2.getCountryId(), order2.getNumber());
    }
}