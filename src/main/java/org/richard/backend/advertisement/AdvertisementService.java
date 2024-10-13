package org.richard.backend.advertisement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public List<Advertisement> getAdvertisements() {
        return advertisementRepository.findAll();
    }

    public List<Advertisement> searchAdvertisementsByTitleFragment(String titleFragment) {
        return advertisementRepository.searchByTitleLikeIgnoreCase(titleFragment);
    }

    public Optional<Advertisement> getAdvertisementById(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
        return advertisementRepository.findById(id);
    }


    @Transactional
    public void createAdvertisement(Advertisement advertisement) {
        if (advertisement.getTitle() == null || advertisement.getTitle().isBlank()) {
            throw new TitleIsBlank(advertisement.getClass().getSimpleName());
        }

        if (advertisementRepository.existsByTitleIgnoreCase(advertisement.getTitle())) {
            throw new DuplicateTitleException("Advertisement", "Title already exists");
        }

        advertisementRepository.save(advertisement);
    }

    @Transactional
    public void updateAdvertisement(UUID id, Advertisement advertisement) {
        Optional<Advertisement> existingAd = advertisementRepository.findById(id);
        if (existingAd.isPresent()) {
            Advertisement existingAdvertisement = existingAd.get();

            if (!existingAdvertisement.getTitle().equalsIgnoreCase(advertisement.getTitle())) {
                if (advertisementRepository.existsByTitleIgnoreCase(advertisement.getTitle())) {
                    throw new DuplicateTitleException("Advertisement", "Title already exists");
                }
            }

            advertisement.setId(id); // Make sure the id stays consistent
            advertisementRepository.save(advertisement);
        } else {
            throw new NotFoundEntityByUuid(advertisement.getClass().getSimpleName(), id.toString());
        }
    }


}
