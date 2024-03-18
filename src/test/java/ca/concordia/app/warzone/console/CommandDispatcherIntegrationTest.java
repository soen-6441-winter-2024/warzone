package ca.concordia.app.warzone.console;

import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommandDispatcherIntegrationTest {

    @MockBean
    private ConsoleRunner runner;

    @Autowired
    private CommandDispatcher controller;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testMapEditorPhase() {

        controller.executeLine("editcontinent -add 1 10");
        controller.executeLine("editcontinent -add 2 10");
        controller.executeLine("editcountry -add 1 1");
        controller.executeLine("editcountry -add 2 1");
        controller.executeLine("editcountry -add 3 1");
        controller.executeLine("editcountry -add 4 2");
        controller.executeLine("editcountry -add 5 2");
        controller.executeLine("editcountry -add 6 2");
        controller.executeLine("editneighbor -add 1 2 -add 1 3 -add 1 4");
        controller.executeLine("editneighbor -add 2 3 -add 2 4 -add 2 5");
        controller.executeLine("editneighbor -add 3 4 -add 3 5 -add 3 6");
        controller.executeLine("editneighbor -add 4 3 -add 4 5 -add 4 6");
        controller.executeLine("editneighbor -add 5 1 -add 5 4 -add 5 6");
        controller.executeLine("editneighbor -add 6 2 -add 6 3 -add 6 4");
        controller.executeLine("showmap");
        controller.executeLine("savemap dir/map_test.txt");
        controller.executeLine("editmap dir/map_test.txt");
        controller.executeLine("validatemap");
        controller.executeLine("nextphase");
        controller.executeLine("showmap");
        controller.executeLine("gameplayer -add player1 -add player2 -add player3");
        controller.executeLine("assigncountries");

        issueOrders("player1");
        issueOrders("player2");
        issueOrders("player3");

    }

    private void issueOrders(String playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);

        assertTrue(playerOpt.isPresent());

        Player player = playerOpt.get();
        List<Country> countries = player.getCountriesAssigned();

        assertThat(countries, hasSize(2));

        controller.executeLine("deploy " + countries.get(0).getId() + " " + 1);
        controller.executeLine("deploy " + countries.get(1).getId() + " " + player.getNumberOfReinforcements());
    }
}
