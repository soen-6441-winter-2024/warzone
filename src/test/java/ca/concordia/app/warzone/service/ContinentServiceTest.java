package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.repository.ContinentRepository;
import ca.concordia.app.warzone.model.Continent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContinentServiceTest {

    @Mock
    private ContinentRepository continentRepository;

    private ContinentService continentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        continentService = new ContinentService(continentRepository);
    }

    @Test
    void testAdd() {
        ContinentDto continentDto = new ContinentDto();
        continentDto.setId("1");
        continentDto.setValue("Asia");

        when(continentRepository.findById("1")).thenReturn(Optional.empty());

        String result = continentService.add(continentDto);

        assertEquals("\nAdding Continent id=1 value=Asia\nContinent id=1 value=Asia added successfully", result);

        verify(continentRepository, times(1)).findById("1");
        verify(continentRepository, times(1)).save(any());
    }

    @Test
    void testFindById() {
        Continent continent = new Continent();
        continent.setId("1");
        continent.setValue("Asia");

        when(continentRepository.findById("1")).thenReturn(Optional.of(continent));

        Optional<ContinentDto> result = continentService.findById("1");

        assertEquals("1", result.get().getId());
        assertEquals("Asia", result.get().getValue());

        verify(continentRepository, times(1)).findById("1");
    }

    @Test
    void testDelete() {
        Continent continent = new Continent();
        continent.setId("1");
        continent.setValue("Asia");

        when(continentRepository.findById("1")).thenReturn(Optional.of(continent));

        String result = continentService.delete("1");

        assertEquals("\nDeleting Continent id=1\nContinent id=1 deleted successfully", result);

        verify(continentRepository, times(1)).findById("1");
        verify(continentRepository, times(1)).deleteById("1");
    }
}

