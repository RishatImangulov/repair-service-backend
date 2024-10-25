package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


class AdvertisementServiceTest {
    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdvertisementService advertisementService;

    private Advertisement advertisement;
    private UUID advertisementId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        advertisementId = UUID.randomUUID();
        advertisement = new Advertisement();
        advertisement.setId(advertisementId);
        advertisement.setTitle("Test Advertisement");
        advertisement.setDescription("Test Description");
    }

    @Test
    public void testGetAdvertisementById_Success() {
        when(advertisementRepository.existsById(advertisementId)).thenReturn(true);
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));

        Optional<AdvertisementDTO> result = advertisementService.getAdvertisementById(advertisementId);

        assertTrue(result.isPresent());
        assertEquals(advertisement.getTitle(), result.get().getTitle());
        verify(advertisementRepository, times(1)).findById(advertisementId);
    }

    @Test
    public void testGetAdvertisementById_NotFound() {
        when(advertisementRepository.existsById(advertisementId)).thenReturn(false);

        NotFoundEntityByUuid exception = assertThrows(NotFoundEntityByUuid.class, () ->
                advertisementService.getAdvertisementById(advertisementId)
        );

        assertEquals("Cant find entity Advertisement with UUID: " + advertisementId, exception.getMessage());
        verify(advertisementRepository, times(1)).existsById(advertisementId);
    }

    @Test
    public void testCreateAdvertisement_Success() {
        when(advertisementRepository.existsByTitleIgnoreCase(anyString())).thenReturn(false);
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(advertisement);

        advertisementService.createAdvertisement(advertisement);

        verify(advertisementRepository, times(1)).existsByTitleIgnoreCase(advertisement.getTitle());
        verify(advertisementRepository, times(1)).save(advertisement);
    }

    @Test
    public void testCreateAdvertisement_TitleIsBlank() {
        advertisement.setTitle("");  // Set blank title

        TitleIsBlank exception = assertThrows(TitleIsBlank.class, () ->
                advertisementService.createAdvertisement(advertisement)
        );

        assertEquals("Title for entity Advertisement can't be blank", exception.getMessage());
        verify(advertisementRepository, times(0)).existsByTitleIgnoreCase(anyString());
    }

    @Test
    public void testCreateAdvertisement_DuplicateTitle() {
        when(advertisementRepository.existsByTitleIgnoreCase(anyString())).thenReturn(true);

        DuplicateTitleException exception = assertThrows(DuplicateTitleException.class, () ->
                advertisementService.createAdvertisement(advertisement)
        );

        assertEquals("Advertisement: Title already exists", exception.getMessage());
        verify(advertisementRepository, times(1)).existsByTitleIgnoreCase(advertisement.getTitle());
        verify(advertisementRepository, times(0)).save(any(Advertisement.class));
    }

    @Test
    public void testUpdateAdvertisement_Success() {
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.existsByTitleIgnoreCase(anyString())).thenReturn(false);

        Advertisement updatedAd = new Advertisement();
        updatedAd.setId(advertisementId);
        updatedAd.setTitle("Updated Title");
        updatedAd.setDescription("Updated Description");

        advertisementService.updateAdvertisement(advertisementId, updatedAd);

        verify(advertisementRepository, times(1)).findById(advertisementId);
        verify(advertisementRepository, times(1)).existsByTitleIgnoreCase(updatedAd.getTitle());
        verify(advertisementRepository, times(1)).save(updatedAd);
    }

    @Test
    public void testUpdateAdvertisement_NotFound() {
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.empty());

        NotFoundEntityByUuid exception = assertThrows(NotFoundEntityByUuid.class, () ->
                advertisementService.updateAdvertisement(advertisementId, advertisement)
        );

        assertEquals("Cant find entity Advertisement with UUID: " + advertisementId, exception.getMessage());
        verify(advertisementRepository, times(1)).findById(advertisementId);
        verify(advertisementRepository, times(0)).save(any(Advertisement.class));
    }

    @Test
    public void testUpdateAdvertisement_DuplicateTitle() {
        when(advertisementRepository.findById(advertisementId)).thenReturn(Optional.of(advertisement));
        when(advertisementRepository.existsByTitleIgnoreCase(anyString())).thenReturn(true);

        Advertisement updatedAd = new Advertisement();
        updatedAd.setId(advertisementId);
        updatedAd.setTitle("New Duplicate Title");
        updatedAd.setDescription("Updated Description");

        DuplicateTitleException exception = assertThrows(DuplicateTitleException.class, () ->
                advertisementService.updateAdvertisement(advertisementId, updatedAd)
        );

        assertEquals("Advertisement: Title already exists", exception.getMessage());
        verify(advertisementRepository, times(1)).findById(advertisementId);
        verify(advertisementRepository, times(1)).existsByTitleIgnoreCase(updatedAd.getTitle());
        verify(advertisementRepository, times(0)).save(any(Advertisement.class));
    }
}