package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.richard.backend.advertisement.*;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private AdvertisementMapper advertisementMapper;

    @InjectMocks
    private AdvertisementService advertisementService;

    private AdvertisementDTO sampleDTO;
    private Advertisement sampleAdvertisement;
    private UUID sampleId;

    @BeforeEach
    public void setUp() {
        sampleId = UUID.randomUUID();
        sampleDTO = new AdvertisementDTO(sampleId, "Sample Title", "Sample Description");
        sampleAdvertisement = new Advertisement(sampleId, "Sample Title", "Sample Description");
    }


    @Test
    public void testCreateAdvertisement_ThrowsTitleIsBlank() {
        sampleDTO.setTitle(" "); // Simulate blank title
        Exception exception = assertThrows(TitleIsBlank.class, () -> advertisementService.createAdvertisement(sampleDTO));
        assertEquals("Title for entity Advertisement can't be blank", exception.getMessage());
    }

    @Test
    public void testCreateAdvertisement_ThrowsDuplicateTitleException() {
        when(advertisementRepository.existsByTitleIgnoreCase(sampleDTO.getTitle())).thenReturn(true);
        assertThrows(DuplicateTitleException.class, () -> advertisementService.createAdvertisement(sampleDTO));
    }

    @Test
    public void testCreateAdvertisement_Success() {
        when(advertisementRepository.existsByTitleIgnoreCase(sampleDTO.getTitle())).thenReturn(false);
        when(advertisementMapper.toEntity(sampleDTO)).thenReturn(sampleAdvertisement);

        advertisementService.createAdvertisement(sampleDTO);

        verify(advertisementRepository, times(1)).save(sampleAdvertisement);
    }

    @Test
    public void testGetAdvertisements() {
        List<Advertisement> ads = List.of(sampleAdvertisement);
        List<AdvertisementDTO> adDTOs = List.of(sampleDTO);

        when(advertisementRepository.findAll()).thenReturn(ads);
        when(advertisementMapper.toDTOList(ads)).thenReturn(adDTOs);

        List<AdvertisementDTO> result = advertisementService.getAdvertisements();

        assertEquals(adDTOs, result);
    }

    @Test
    public void testGetAdvertisementById_Success() {
        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.of(sampleAdvertisement));
        when(advertisementMapper.toDTO(sampleAdvertisement)).thenReturn(sampleDTO);

        AdvertisementDTO result = advertisementService.getAdvertisementById(sampleId);

        assertEquals(sampleDTO, result);
    }

    @Test
    public void testGetAdvertisementById_ThrowsNotFound() {
        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.getAdvertisementById(sampleId));
    }

    @Test
    public void testUpdateAdvertisement_ThrowsDuplicateTitleException() {
        AdvertisementDTO updatedDTO = new AdvertisementDTO(sampleId, "New Title", "Updated Description");
        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.of(sampleAdvertisement));
        when(advertisementRepository.existsByTitleIgnoreCase(updatedDTO.getTitle())).thenReturn(true);

        assertThrows(DuplicateTitleException.class, () -> advertisementService.updateAdvertisement(sampleId, updatedDTO));
    }

    @Test
    public void testUpdateAdvertisement_Success() {
        AdvertisementDTO updatedDTO = new AdvertisementDTO(sampleId, "Updated Title", "Updated Description");
        Advertisement updatedAd = new Advertisement(sampleId, "Updated Title", "Updated Description");

        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.of(sampleAdvertisement));
        when(advertisementRepository.existsByTitleIgnoreCase(updatedDTO.getTitle())).thenReturn(false);
        when(advertisementMapper.updateEntityFromDTO(sampleAdvertisement, updatedDTO)).thenReturn(updatedAd);

        advertisementService.updateAdvertisement(sampleId, updatedDTO);

        verify(advertisementRepository, times(1)).save(updatedAd);
    }

    @Test
    public void testUpdateAdvertisement_ThrowsNotFound() {
        AdvertisementDTO updatedDTO = new AdvertisementDTO(UUID.randomUUID(), "New Title", "Updated Description");
        when(advertisementRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.updateAdvertisement(UUID.randomUUID(), updatedDTO));
    }


    @Test
    public void testDeleteAdvertisement_Success() {
        when(advertisementRepository.existsById(sampleId)).thenReturn(true);

        advertisementService.deleteAdvertisement(sampleId);

        verify(advertisementRepository, times(1)).deleteById(sampleId);
    }

    @Test
    public void testDeleteAdvertisement_ThrowsNotFound() {
        when(advertisementRepository.existsById(sampleId)).thenReturn(false);

        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.deleteAdvertisement(sampleId));
    }
}





