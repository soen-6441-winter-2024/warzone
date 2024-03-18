package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.ConsoleRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MapServiceTest {

    public static final String VALID_FILE_NAME = "src/test/resources/valid_map.txt";
    public static final String VALID_FILE_NAME_EMPTY_BORDERS = "src/test/resources/valid_map_empty_section.txt";
    public static final String INVALID_FILE_NAME_EMPTY_COUNTRIES = "src/test/resources/invalid_map_empty_countries.txt";
    public static final String INVALID_FILE_NAME = "src/test/resources/invalid_map.txt";

    @MockBean
    private ConsoleRunner consoleRunner;

    @Autowired
    private MapService underTest;

    @Test
    public void testValidateMapStructure() {

        boolean result = underTest.validateMapStructure(VALID_FILE_NAME);

        assertTrue(result);
    }

    @Test
    public void testValidateMapStructureEmptyBorders() {

        boolean result = underTest.validateMapStructure(VALID_FILE_NAME_EMPTY_BORDERS);

        assertFalse(result);
    }

    @Test
    public void testValidateMapStructureEmptyCountries() {

        boolean result = underTest.validateMapStructure(INVALID_FILE_NAME_EMPTY_COUNTRIES);

        assertFalse(result);
    }

    @Test
    public void testValidateMapStructure_Invalid() {

        boolean result = underTest.validateMapStructure(INVALID_FILE_NAME);

        assertFalse(result);
    }
}
