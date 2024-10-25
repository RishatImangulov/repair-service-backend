package org.richard.backend.advertisement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public AdvertisementDTO getAdvertisementById(UUID id) {
        return advertisementRepository.findById(id)
                .map(advertisementMapper::toDTO)
                .orElseThrow(() -> new NotFoundEntityByUuid("Advertisement", id.toString()));

    }

    @Transactional
    public void createAdvertisement(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO.getTitle() == null || advertisementDTO.getTitle().isBlank()) {
            throw new TitleIsBlank("Advertisement");
        }

        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());

        if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
            throw new DuplicateTitleException("Advertisement", "Title already exists");
        }

        advertisementRepository.save(advertisementMapper.toEntity(advertisementDTO));
    }

    @Transactional
    public void updateAdvertisement(UUID id, AdvertisementDTO advertisementDTO) {

        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());

        advertisementRepository.findById(id).ifPresentOrElse(existingAd -> {
            if (!existingAd.getTitle().equalsIgnoreCase(advertisementDTO.getTitle())) {
                if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
                    throw new DuplicateTitleException("Advertisement", "Title already exists");
                }
            }
            // TODO why it is necessary?
            advertisementDTO.setId(id); // Make sure the id stays consistent
            advertisementRepository.save(advertisementMapper.updateEntityFromDTO(existingAd, advertisementDTO));
        }, () -> {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        });
    }

    @Transactional
    public void deleteAdvertisement(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
        advertisementRepository.deleteById(id);
    }
}
