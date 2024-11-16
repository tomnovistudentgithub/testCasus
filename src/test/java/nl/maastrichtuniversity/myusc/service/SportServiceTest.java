package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.dtos.SportDto;
import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.model.SportType;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SportServiceTest {

    private SportRepository sportRepository;

    private SportService sportService;

    @BeforeEach
    public void setUp() {
        sportRepository = Mockito.mock(SportRepository.class);
        sportService = new SportService(sportRepository);
    }

    @Test
    public void testAddSport_Success() {
        // Arrange
        SportDto sportDto = new SportDto();
        sportDto.setName("Voetbal");
        sportDto.setSportType(SportType.SPORTS);

        when(sportRepository.findByNameIgnoreCase("Voetbal")).thenReturn(Optional.empty());

        // Act
        sportService.addSport(sportDto);

        // Assert
        verify(sportRepository, times(1)).save(any(Sport.class));
    }

    @Test
    public void testAddSport_AlreadyExists() {
        // Arrange
        SportDto sportDto = new SportDto();
        sportDto.setName("Voetbal");

        Sport existingSport = new Sport();
        existingSport.setName("Voetbal");

        when(sportRepository.findByNameIgnoreCase("Voetbal")).thenReturn(Optional.of(existingSport));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> sportService.addSport(sportDto));
    }

    @Test
    public void testDeleteSport_Success() {
        // Arrange
        when(sportRepository.existsById(1L)).thenReturn(true);

        // Act
        sportService.deleteSport(1L);

        // Assert
        verify(sportRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSport_NotExists() {
        // Arrange
        when(sportRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> sportService.deleteSport(1L));
    }
}
