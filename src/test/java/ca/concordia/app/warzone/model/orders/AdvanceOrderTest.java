package ca.concordia.app.warzone.model.orders;

import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.service.CountryService;
import ca.concordia.app.warzone.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdvanceOrderTest {

    private AdvanceOrder advanceOrder;
    private CountryService countryServiceMock;

    @BeforeEach
    void setUp() {
        countryServiceMock = mock(CountryService.class);
        advanceOrder = new AdvanceOrder("player1", "France", "Germany", 5, countryServiceMock);
    }

    @Test
    void execute_successfulAdvance() throws NotFoundException {
        Player player = new Player();
        player.setPlayerName("player1");

        Country countryFrom = new Country();
        countryFrom.setId("France");
        countryFrom.setPlayer(Optional.of(player));

        player.addCountry(countryFrom);
        Country countryTo = new Country();
        countryTo.setId("Germany");
        countryTo.setPlayer(Optional.of(player));

        countryFrom.setPlayer(Optional.of(player));
        countryFrom.setArmiesCount(5);
        player.addCountry(countryTo);

        when(countryServiceMock.findCountryById("France")).thenReturn(Optional.of(countryFrom));
        when(countryServiceMock.findCountryById("Germany")).thenReturn(Optional.of(countryTo));
        when(countryServiceMock.areNeighbors("France", "Germany")).thenReturn(true);

        advanceOrder.execute();

        assertEquals(player, countryTo.getPlayer().get());
    }

    @Test
    void execute_insufficientArmies() throws NotFoundException {
        Country countryFrom = new Country();
        countryFrom.setPlayer(Optional.of(new Player()));
        countryFrom.setArmiesCount(3);

        when(countryServiceMock.findCountryById("countryFrom")).thenReturn(Optional.of(countryFrom));
        when(countryServiceMock.areNeighbors("countryFrom", "countryTo")).thenReturn(true);

        advanceOrder.execute();

        assertEquals(3, countryFrom.getArmiesCount());
    }
}
