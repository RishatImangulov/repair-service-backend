//package org.richard.backend.advertisement;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.richard.backend.exception.DuplicateTitleException;
//import org.richard.backend.exception.NotFoundEntityByUuid;
//import org.richard.backend.exception.TitleIsBlank;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AdvertisementServiceTest {
//
//    @Mock
//    private AdvertisementRepository advertisementRepository;
//
//    @Mock
//    private AdvertisementMapper advertisementMapper;
//
//    @InjectMocks
//    private AdvertisementService advertisementService;
//
//    private AdvertisementDTO sampleDTO;
//    private Advertisement sampleAdvertisement;
//    private UUID sampleId;
//
//    @BeforeEach
//    public void setUp() {
//        sampleId = UUID.randomUUID();
//        sampleDTO = new AdvertisementDTO(sampleId, "Sample Title", "Sample Description");
//        sampleAdvertisement = new Advertisement(sampleId, "Sample Title", "Sample Description");
//    }
//
//
//    @Test
//    public void testCreate_ThrowsTitleIsBlank() {
//        sampleDTO.setTitle(" "); // Simulate blank title
//        Exception exception = assertThrows(TitleIsBlank.class, () -> advertisementService.create(sampleDTO));
//        assertEquals("Title for entity Advertisement can't be blank", exception.getMessage());
//    }
//
//    @Test
//    public void testCreate_ThrowsDuplicateTitleException() {
//        when(advertisementRepository.existsByTitleIgnoreCase(sampleDTO.getTitle())).thenReturn(true);
//        assertThrows(DuplicateTitleException.class, () -> advertisementService.create(sampleDTO));
//    }
//
//    @Test
//    public void testCreate_Success() {
//        when(advertisementRepository.existsByTitleIgnoreCase(sampleDTO.getTitle())).thenReturn(false);
//        when(advertisementMapper.toEntity(sampleDTO)).thenReturn(sampleAdvertisement);
//
//        advertisementService.create(sampleDTO);
//
//        verify(advertisementRepository, times(1)).save(sampleAdvertisement);
//    }
//
//    @Test
//    public void testGetAll() {
//        List<Advertisement> ads = List.of(sampleAdvertisement);
//        List<AdvertisementDTO> adDTOs = List.of(sampleDTO);
//
//        when(advertisementRepository.findAll()).thenReturn(ads);
//        when(advertisementMapper.toDTOList(ads)).thenReturn(adDTOs);
//
//        List<AdvertisementDTO> result = advertisementService.getAll();
//
//        assertEquals(adDTOs, result);
//    }
//
//    @Test
//    public void testGetById_Success() {
//        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.of(sampleAdvertisement));
//        when(advertisementMapper.toDTO(sampleAdvertisement)).thenReturn(sampleDTO);
//
//        AdvertisementDTO result = advertisementService.getById(sampleId);
//
//        assertEquals(sampleDTO, result);
//    }
//
//    @Test
//    public void testGetById_ThrowsNotFound() {
//        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.getById(sampleId));
//    }
//
//    @Test
//    public void testUpdate_ThrowsDuplicateTitleException() {
//        AdvertisementDTO updatedDTO = new AdvertisementDTO(sampleId, "New Title", "Updated Description");
//        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.of(sampleAdvertisement));
//        when(advertisementRepository.existsByTitleIgnoreCase(updatedDTO.getTitle())).thenReturn(true);
//
//        assertThrows(DuplicateTitleException.class, () -> advertisementService.update(sampleId, updatedDTO));
//    }
//
//    @Test
//    public void testUpdate_Success() {
//        AdvertisementDTO updatedDTO = new AdvertisementDTO(sampleId, "Updated Title", "Updated Description");
//        Advertisement updatedAd = new Advertisement(sampleId, "Updated Title", "Updated Description");
//
//        when(advertisementRepository.findById(sampleId)).thenReturn(Optional.of(sampleAdvertisement));
//        when(advertisementRepository.existsByTitleIgnoreCase(updatedDTO.getTitle())).thenReturn(false);
//        when(advertisementMapper.updateEntityFromDTO(sampleAdvertisement, updatedDTO)).thenReturn(updatedAd);
//
//        advertisementService.update(sampleId, updatedDTO);
//
//        verify(advertisementRepository, times(1)).save(updatedAd);
//    }
//
//    @Test
//    public void testUpdate_ThrowsNotFound() {
//        AdvertisementDTO updatedDTO = new AdvertisementDTO(UUID.randomUUID(), "New Title", "Updated Description");
//        when(advertisementRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.update(UUID.randomUUID(), updatedDTO));
//    }
//
//
//    @Test
//    public void testDelete_Success() {
//        when(advertisementRepository.existsById(sampleId)).thenReturn(true);
//
//        advertisementService.delete(sampleId);
//
//        verify(advertisementRepository, times(1)).deleteById(sampleId);
//    }
//
//    @Test
//    public void testDelete_ThrowsNotFound() {
//        when(advertisementRepository.existsById(sampleId)).thenReturn(false);
//
//        assertThrows(NotFoundEntityByUuid.class, () -> advertisementService.delete(sampleId));
//    }
//}
//
//
//
//
//
