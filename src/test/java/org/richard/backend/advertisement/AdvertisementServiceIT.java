//package org.richard.backend.advertisement;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class AdvertisementServiceIT {
//
//    @Autowired
//    private AdvertisementService advertisementService;
//
//    @Autowired
//    private AdvertisementRepository advertisementRepository;
//
//    @Test
//    public void testCreate() {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields, e.g., dto.setTitle("Test Ad"), etc.
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//        assertNotNull(savedDto.getId());
//        assertEquals("Test Ad", savedDto.getTitle());
//    }
//
//    @Test
//    public void testFindAdvertisementById() {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields, e.g., dto.setTitle("Find Test Ad"), etc.
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//        AdvertisementDTO foundDto = advertisementService.findById(savedDto.getId());
//        assertEquals(savedDto.getId(), foundDto.getId());
//    }
//
//    @Test
//    public void testUpdate() {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields, e.g., dto.setTitle("Original Title"), etc.
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//        savedDto.setTitle("Updated Title");
//        AdvertisementDTO updatedDto = advertisementService.update(savedDto.getId(), savedDto);
//        assertEquals("Updated Title", updatedDto.getTitle());
//    }
//
//    @Test
//    public void testDelete() {
//        AdvertisementDTO dto = new AdvertisementDTO();
//        // Set DTO fields
//
//        AdvertisementDTO savedDto = advertisementService.create(dto);
//        advertisementService.delete(savedDto.getId());
//        assertFalse(advertisementRepository.existsById(savedDto.getId()));
//    }
//}
