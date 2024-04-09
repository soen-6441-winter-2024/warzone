package ca.concordia.app.warzone.service;

import static org.junit.jupiter.api.Assertions.*;

import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.model.Order;
import ca.concordia.app.warzone.model.Strategies.HumanPlayerStrategy;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.model.orders.DeployOrder;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.ContinentService;
import ca.concordia.app.warzone.service.MapService;
import org.mockito.Spy;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CountryService countryService;

    @Mock
    private ContinentService continentService;

    @Mock
    private MapService mapService;


    private List<List<Order>> d_orders;

    private int d_currentRound;

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private HumanPlayerStrategy humanPlayerStrategy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository, mapService, countryService, continentService, humanPlayerStrategy);
    }

    @Test
    public void testGetAllPlayers() {
        // Arrange
        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        player1.setPlayerName("Player 1");
        players.add(player1);

        Player player2 = new Player();
        player1.setPlayerName("Player 2");
        players.add(player2);

        when(playerRepository.findAll()).thenReturn(players);

        // Act
        List<Player> result = playerService.getAllPlayers();

        // Assert
        assertEquals(players.size(), result.size());
        assertEquals(players.get(0).getPlayerName(), result.get(0).getPlayerName());
        assertEquals(players.get(1).getPlayerName(), result.get(1).getPlayerName());
    }

    @Test
    public void testFindByName_PlayerExists() {
        // Arrange
        String playerName = "Player1";
        Player player = new Player();
        player.setPlayerName(playerName);
        when(playerRepository.findById(playerName)).thenReturn(Optional.of(player));

        // Act
        Optional<Player> result = playerService.findByName(playerName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(playerName, result.get().getPlayerName());
    }

    @Test
    public void testFindByName_PlayerDoesNotExist() {
        // Arrange
        String playerName = "NonExistingPlayer";
        when(playerRepository.findById(playerName)).thenReturn(Optional.empty());

        // Act
        Optional<Player> result = playerService.findByName(playerName);

        // Assert
        assertTrue(result.isEmpty());
    }





}
