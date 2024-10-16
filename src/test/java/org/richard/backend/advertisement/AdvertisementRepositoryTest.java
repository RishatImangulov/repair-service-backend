package org.richard.backend.advertisement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class AdvertisementRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

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
        List<Advertisement> results = advertisementRepository.findByTitleContainingIgnoreCase("sale");

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

    @Test
    public void testSearchByTitleWithNoMatches() {
        // Given
        Advertisement ad1 = new Advertisement(UUID.randomUUID(), "Black Friday Sale", "Best deals on electronics");
        Advertisement ad2 = new Advertisement(UUID.randomUUID(), "Summer Clearance", "Clearance on summer items");
        advertisementRepository.save(ad1);
        advertisementRepository.save(ad2);

        // When
        List<Advertisement> results = advertisementRepository.findByTitleContainingIgnoreCase("Christmas");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    public void testSearchByPartialTitleIgnoreCase() {
        // Given
        Advertisement ad1 = new Advertisement(UUID.randomUUID(), "Spring Sale", "Fresh spring discounts");
        Advertisement ad2 = new Advertisement(UUID.randomUUID(), "Autumn Sale", "End of year clearance");
        advertisementRepository.save(ad1);
        advertisementRepository.save(ad2);

        // When
        List<Advertisement> results = advertisementRepository.findByTitleContainingIgnoreCase("spring");

        // Then
        assertThat(results).hasSize(1).extracting(Advertisement::getTitle).contains("Spring Sale");
    }

    @Test
    public void testExistsByTitleIgnoreCaseExactMatch() {
        // Given
        Advertisement ad = new Advertisement(UUID.randomUUID(), "Black Friday Sale", "Biggest sale of the year");
        advertisementRepository.save(ad);

        // When
        boolean exists = advertisementRepository.existsByTitleIgnoreCase("BLACK FRIDAY SALE");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByTitleIgnoreCaseNonExistent() {
        // When
        boolean exists = advertisementRepository.existsByTitleIgnoreCase("Nonexistent Sale");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    public void testSearchByTitleWithMultipleMatches() {
        // Given
        Advertisement ad1 = new Advertisement(UUID.randomUUID(), "Holiday Sale", "Best holiday deals");
        Advertisement ad2 = new Advertisement(UUID.randomUUID(), "Holiday Clearance", "End of season clearance");
        advertisementRepository.save(ad1);
        advertisementRepository.save(ad2);

        // When
        List<Advertisement> results = advertisementRepository.findByTitleContainingIgnoreCase("Holiday");

        // Then
        assertThat(results).hasSize(2);
    }

}
