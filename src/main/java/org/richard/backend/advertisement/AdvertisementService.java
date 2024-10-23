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
    private final AdvertisementMapper advertisementMapper;

    public List<AdvertisementDTO> getAdvertisements() {
        return advertisementMapper.toDTOList(
                advertisementRepository.findAll());
    }

    // should I check blank fragment?
    public List<AdvertisementDTO> searchAdvertisementsByTitleFragment(String titleFragment) {
        return advertisementMapper.toDTOList(
                advertisementRepository.findByTitleContainingIgnoreCase(titleFragment));
    }

    public Optional<AdvertisementDTO> getAdvertisementById(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
        return advertisementRepository.findById(id);
    }

    @Transactional
    public void createAdvertisement(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO.getTitle() == null || advertisementDTO.getTitle().isBlank()) {
            throw new TitleIsBlank(advertisementDTO.getClass().getSimpleName());
        }

        if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
            throw new DuplicateTitleException("Advertisement", "Title already exists");
        }

        advertisementRepository.save(advertisementDTO);
    }

    @Transactional
    public void updateAdvertisement(UUID id, AdvertisementDTO advertisementDTO) {
        Optional<Advertisement> existingAd = advertisementRepository.findById(id);
        if (existingAd.isPresent()) {
            Advertisement existingAdvertisement = existingAd.get();

            if (!existingAdvertisement.getTitle().equalsIgnoreCase(advertisementDTO.getTitle())) {
                if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
                    throw new DuplicateTitleException("Advertisement", "Title already exists");
                }
            }

            advertisementDTO.setId(id); // Make sure the id stays consistent
            advertisementRepository.save(advertisementDTO);
        } else {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
    }
}
