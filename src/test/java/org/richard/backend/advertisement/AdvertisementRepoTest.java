package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//@Testcontainers
@DataJpaTest
@SpringBootTest
//@
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AdvertisementRepoTest {



//    @Container
//    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
//            .withDatabaseName("testdb")
//            .withUsername("testuser")
//            .withPassword("testpass");
//    @DynamicPropertySource
//    static void overrideProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgresContainer::getUsername);
//        registry.add("spring.datasource.password", postgresContainer::getPassword);
//    }

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @BeforeEach
    public void setUp() {
        // Clear database before each test
        advertisementRepository.deleteAll();
    }

    @Test
    public void testSearchByTitleLikeIgnoreCase() {
        // Given
        Advertisement ad1 = new Advertisement(UUID.randomUUID(), "Great Sale", "Best sale of the year!");
        Advertisement ad2 = new Advertisement(UUID.randomUUID(), "Garage Sale", "Huge discounts!");
        advertisementRepository.save(ad1);
        advertisementRepository.save(ad2);

        // When
        List<Advertisement> results = advertisementRepository.searchByTitleLikeIgnoreCase("sale");

        // Then
        assertThat(results).hasSize(2);
    }

    @Test
    public void testExistsByTitleIgnoreCase() {
        // Given
        Advertisement ad = new Advertisement(UUID.randomUUID(), "Winter Sale", "Huge winter discounts!");
        advertisementRepository.save(ad);

        // When
        boolean exists = advertisementRepository.existsByTitleIgnoreCase("winter sale");

        // Then
        assertThat(exists).isTrue();
    }
}
