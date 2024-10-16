//package org.richard.backend.advertisement;
//
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.richard.backend.advertisement.Advertisement;
//import org.richard.backend.advertisement.AdvertisementRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@Sql("/sql/advertisements.sql")
//@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@ActiveProfiles("test")
////@Testcontainers
//public class AdvertisementRepositoryTest {
//
////    @Container  // The container to be used in the test
////    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.0")
////            .withDatabaseName("test_db")
////            .withUsername("test_user")
////            .withPassword("test_password");
//
//    @Autowired
//    private AdvertisementRepository advertisementRepository;
//
//    private Advertisement advertisement1;
//    private Advertisement advertisement2;
//
////    @BeforeEach
////    public void setUp() {
////        advertisement1 = new Advertisement();
////        advertisement1.setId(UUID.randomUUID());
////        advertisement1.setTitle("Google Ads");
////        advertisement1.setDescription("An advertisement for Google");
////
////        advertisement2 = new Advertisement();
////        advertisement2.setId(UUID.randomUUID());
////        advertisement2.setTitle("Bing Ads");
////        advertisement2.setDescription("An advertisement for Bing");
////
////        advertisementRepository.save(advertisement1);
////        advertisementRepository.save(advertisement2);
////    }
//
//    @Test
//    public void customTestSearchByTitleLikeIgnoreCase_FindsResults() {
//        var result = advertisementRepository.searchByTitleLikeIgnoreCase("oo");
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }
//
//    @Test
//    public void testSearchByTitleLikeIgnoreCase_FindsResults() {
//        List<Advertisement> result = advertisementRepository.searchByTitleLikeIgnoreCase("goo");
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals("Google Ads", result.get(0).getTitle());
//    }
//
//    @Test
//    public void testSearchByTitleLikeIgnoreCase_NoResults() {
//        List<Advertisement> result = advertisementRepository.searchByTitleLikeIgnoreCase("yahoo");
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void testExistsByTitleIgnoreCase_ReturnsTrueIfExists() {
//        boolean exists = advertisementRepository.existsByTitleIgnoreCase("google ads");
//
//        assertTrue(exists);
//    }
//
//    @Test
//    public void testExistsByTitleIgnoreCase_ReturnsFalseIfNotExists() {
//        boolean exists = advertisementRepository.existsByTitleIgnoreCase("facebook ads");
//
//        assertFalse(exists);
//    }
//}
