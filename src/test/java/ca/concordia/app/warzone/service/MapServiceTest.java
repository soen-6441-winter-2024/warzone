package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.repository.MapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MapServiceTest {

    public static final String VALID_FILE_NAME = "src/test/resources/valid_map.txt";
    public static final String INVALID_FILE_NAME = "src/test/resources/invalid_map.txt";

    @Mock
    private MapRepository mapRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private ContinentRepository continentRepository;
    private MapService underTest;

    @BeforeEach
    public void before() {
        underTest = new MapService(mapRepository, countryRepository, continentRepository);
    }

    @Test
    public void testValidateMapStructure() {

        boolean result = underTest.validateMapStructure(VALID_FILE_NAME);

        assertTrue(result);
    }

    @Test
    public void testValidateMapStructure_Invalid() {

        boolean result = underTest.validateMapStructure(INVALID_FILE_NAME);

        assertFalse(result);
    }
}
