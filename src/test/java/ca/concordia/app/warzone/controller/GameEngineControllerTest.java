package ca.concordia.app.warzone.controller;

import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameEngineControllerTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CountryService countryService;

    @Mock
    private ContinentService continentService;

    @Mock
    private MapService mapService;

    @Mock
    private OrdersService ordersService;



    private List<List<Order>> d_orders;

    private int d_currentRound;

    @Mock
    private PlayerCardService playerCardService;

    @InjectMocks
    private PlayerService playerService;

    @InjectMocks
    private GameEngineController gameEngineController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository, mapService, countryService, ordersService, continentService);
        gameEngineController = new GameEngineController(continentService, countryService, playerService, mapService, ordersService, null, playerCardService);
    }

    @Test
    public void startGameLoop() {
        Player player = new Player();
        player.setPlayerName("John");
        Player player2 = new Player();
        player2.setPlayerName("John2");
        when(playerRepository.findAll()).thenReturn(Collections.singletonList(new Player()));

        try{
            gameEngineController.startGameLoop();
        } catch (Exception e){
            assertEquals(1, playerService.getAllPlayers().size());
            verify(playerRepository, times(2)).findAll();
        }

    }
}