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

    /**
     * Retrieves all advertisements from the repository.
     *
     * @return List of all advertisements
     */
    public List<Advertisement> getAdvertisements() {
        return advertisementRepository.findAll();
    }

    /**
     * Searches for advertisements whose title contains the specified fragment, ignoring case.
     *
     * @param titleFragment the fragment of the title to search for
     * @return List of advertisements matching the search criteria
     */
    public List<Advertisement> searchAdvertisementsByTitleFragment(String titleFragment) {
        return advertisementRepository.findByTitleContainingIgnoreCase(titleFragment);
    }

    /**
     * Retrieves an advertisement by its UUID.
     * If the advertisement does not exist, a NotFoundEntityByUuid exception is thrown.
     *
     * @param id the UUID of the advertisement to retrieve
     * @return Optional containing the advertisement, or empty if not found
     */
    public Optional<Advertisement> getAdvertisementById(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
        return advertisementRepository.findById(id);
    }

    /**
     * Creates a new advertisement.
     * Throws TitleIsBlank if the title is null or blank, and DuplicateTitleException if the title already exists.
     *
     * @param advertisement the Advertisement object to create
     */
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

    /**
     * Updates an existing advertisement.
     * Ensures that the advertisement exists, checks for duplicate titles if the title is changed,
     * and maintains the consistency of the UUID.
     * <p>
     * Throws NotFoundEntityByUuid if the advertisement does not exist,
     * and DuplicateTitleException if the new title already exists.
     *
     * @param id            the UUID of the advertisement to update
     * @param advertisement the updated Advertisement object
     */
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
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
    }
}
