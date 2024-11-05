package org.richard.backend.advertisement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.entity.EntityType;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;

    public List<AdvertisementDTO> getAll() {
        return advertisementMapper.toDTOList(
                advertisementRepository.findAll());
    }

    // should I check blank fragment?
    public List<AdvertisementDTO> findAllByTitleFragment(String titleFragment) {
        return advertisementMapper.toDTOList(
                advertisementRepository.findByTitleContainingIgnoreCase(titleFragment));
    }

    public AdvertisementDTO getById(UUID id) {
        return advertisementRepository.findById(id)
                .map(advertisementMapper::toDTO)
                .orElseThrow(() -> new NotFoundEntityByUuid(EntityType.ADVERTISEMENT.getDisplayName(Locale.getDefault()),
                        id.toString()));

    }

    @Transactional
    public AdvertisementDTO create(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO.getTitle() == null || advertisementDTO.getTitle().isBlank()) {
            throw new TitleIsBlank(EntityType.ADVERTISEMENT.getDisplayName(Locale.getDefault()));
        }

        advertisementDTO.setId(null); // because we are creating
        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());


        if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
            throw new DuplicateTitleException(EntityType.ADVERTISEMENT.getDisplayName(Locale.getDefault()),
                    "Title already exists");
        }

        var saved = advertisementRepository.save(advertisementMapper.toEntity(advertisementDTO));
        return advertisementMapper.toDTO(saved);
    }

    @Transactional
    public AdvertisementDTO update(UUID id, AdvertisementDTO advertisementDTO) {

        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());

        advertisementRepository.findById(id).ifPresentOrElse(existingAd -> {
            if (!existingAd.getTitle().equalsIgnoreCase(advertisementDTO.getTitle())) {
                if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
                    throw new DuplicateTitleException(EntityType.ADVERTISEMENT.getDisplayName(Locale.getDefault()),
                            "Title already exists");
                }
            }
            // TODO why it is necessary?
            advertisementDTO.setId(id); // Make sure the id stays consistent
            advertisementRepository.save(advertisementMapper.updateEntityFromDTO(existingAd, advertisementDTO));
        }, () -> {
            throw new NotFoundEntityByUuid(EntityType.ADVERTISEMENT.getDisplayName(Locale.getDefault()), id.toString());
        });
        // TODO  - uuid correct?
        return advertisementDTO;

    }

    @Transactional
    public void delete(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid(EntityType.ADVERTISEMENT.getDisplayName(Locale.getDefault()), id.toString());
        }
        advertisementRepository.deleteById(id);
    }
}
