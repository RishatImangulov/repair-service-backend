package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdvertisementController.class)
public class AdvertisementServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AdvertisementServiceTest.class);
    @MockBean
    private AdvertisementService advertisementService;

    @InjectMocks
    private AdvertisementController advertisementController;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private AdvertisementMapper advertisementMapper;

    private AdvertisementDTO sampleDTO;
    private Advertisement sampleEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sampleDTO = new AdvertisementDTO(UUID.randomUUID(), "Sample Title", "Sample Description");
        sampleEntity = new Advertisement(UUID.randomUUID(), "Sample Title", "Sample Description");
    }

    @Test
    public void testGetAllAdvertisements() {
        when(advertisementService.getAdvertisements()).thenReturn(List.of(sampleDTO));
        assert advertisementService.getAdvertisements().size() == 1;
        verify(advertisementService, times(1)).getAdvertisements();
    }

    @Test
    public void testCreateAdvertisement() {
        doNothing().when(advertisementService).createAdvertisement(sampleDTO);
        advertisementService.createAdvertisement(sampleDTO);
        verify(advertisementService, times(1)).createAdvertisement(sampleDTO);
    }

    @Test
    public void testDuplicateTitleException() {
        when(advertisementRepository.existsByTitleIgnoreCase(sampleDTO.getTitle())).thenReturn(true);
        System.out.printf("ff");
        assertThrows(DuplicateTitleException.class, () -> advertisementService.createAdvertisement(sampleDTO));
    }

    @Test
    public void testGetAdvertisementByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(advertisementRepository.findById(id)).thenThrow(new NotFoundEntityByUuid("Advertisement", id.toString()));
        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.getAdvertisementById(id));
    }

    @Test
    public void testSearchAdvertisementsByTitleFragment() {
        when(advertisementService.searchAdvertisementsByTitleFragment("Sample")).thenReturn(List.of(sampleDTO));
        assert advertisementService.searchAdvertisementsByTitleFragment("Sample").size() == 1;
    }



}
