package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ContinentService continentService;

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        countryService = new CountryService(countryRepository, continentService);
    }

    @Test
    void testAdd() {
        CountryDto countryDto = new CountryDto();
        countryDto.setId("1");
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId("1");
        countryDto.setContinent(continentDto);

        when(continentService.findById("1")).thenReturn(Optional.of(continentDto));
        when(countryRepository.findById("1")).thenReturn(Optional.empty());

        String result = countryService.add(countryDto);

        assertEquals("\nAdding Country id=1 Continent id=1\nCountry id=1 Continent id=1 added successfully", result);

        verify(continentService, times(1)).findById("1");
        verify(countryRepository, times(1)).findById("1");
        verify(countryRepository, times(1)).save(any());
    }

    @Test
    void testFindById() {
        Country country = new Country();
        Continent continent = new Continent();
        country.setId("1");
        country.setContinent(continent);

        when(countryRepository.findById("1")).thenReturn(Optional.of(country));

        Optional<CountryDto> result = countryService.findById("1");

        assertEquals("1", result.get().getId());

        verify(countryRepository, times(1)).findById("1");
    }

    @Test
    void testDelete() {
        Country country = new Country();
        country.setId("1");

        when(countryRepository.findById("1")).thenReturn(Optional.of(country));

        String result = countryService.delete("1");

        assertEquals("\nDeleting Country id=1\nCountry id=1 deleted successfully", result);

        verify(countryRepository, times(1)).findById("1");
        verify(countryRepository, times(1)).deleteById("1");
    }
}

