package org.richard.backend.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    List<Advertisement> searchByTitleLikeIgnoreCase(String fragment);

    boolean existsByTitleIgnoreCase(String title);

}
