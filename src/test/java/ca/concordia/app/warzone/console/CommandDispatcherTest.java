package ca.concordia.app.warzone.console;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CommandDispatcherTest {

    @MockBean
    private ConsoleRunner runner;

    @Autowired
    private CommandDispatcher controller;

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

    }
}
