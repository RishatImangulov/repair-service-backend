package org.richard.backend.advertisement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.entity.EntityName;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    //    private final AdvertisementMapper advertisementMapper;
    private final ConversionService conversionService;

    public List<AdvertisementDTO> getAll() {
        return advertisementRepository.findAll()
                .stream()
                .map(adv -> conversionService.convert(adv, AdvertisementDTO.class))
                .toList();
    }

    // should I check blank fragment?
    public List<AdvertisementDTO> findAllByTitleFragment(String titleFragment) {
        return
                advertisementRepository.findByTitleContainingIgnoreCase(titleFragment)
                        .stream()
                        .map(avd -> conversionService.convert(avd, AdvertisementDTO.class))
                        .toList();
    }

    public AdvertisementDTO getById(UUID id) {
        return advertisementRepository.findById(id)
                .map(adv -> conversionService.convert(adv, AdvertisementDTO.class))
                .orElseThrow(() -> new NotFoundEntityByUuid(EntityName.ADVERTISEMENT.getDisplayName(Locale.getDefault()),
                        id.toString()));

    }

    @Transactional
    public AdvertisementDTO create(AdvertisementDTO advertisementDTO) {

        if (advertisementDTO.getTitle() == null || advertisementDTO.getTitle().isBlank()) {
            throw new TitleIsBlank(EntityName.ADVERTISEMENT.getDisplayName(Locale.getDefault()));
        }

        advertisementDTO.setId(null); // because we are creating
        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());


        if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
            throw new DuplicateTitleException(EntityName.ADVERTISEMENT.getDisplayName(Locale.getDefault()),
                    Locale.forLanguageTag("ru"));
        }

        var saved = advertisementRepository.save(
                Objects.requireNonNull(conversionService.convert(advertisementDTO, Advertisement.class),
                        "Conversion failed: advertisementDTO could not be converted to Advertisement"));
        return conversionService.convert(saved, AdvertisementDTO.class);
    }

    @Transactional
    // TODO i need to return some thing?  cause i already have needed DTO in front..
    public AdvertisementDTO update(UUID id, AdvertisementDTO advertisementDTO) {

        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());

        advertisementRepository.findById(id).ifPresentOrElse(existingAd -> {
            if (!existingAd.getTitle().equalsIgnoreCase(advertisementDTO.getTitle())) {
                if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
                    throw new DuplicateTitleException(EntityName.ADVERTISEMENT.getDisplayName(Locale.getDefault()),
                            Locale.forLanguageTag("ru"));
                }
            }
            // TODO why it is necessary?
            advertisementDTO.setId(id); // Make sure the id stays consistent
//            conversionService.updateEntityFromDTO(advertisementDTO, existingAd);

            advertisementRepository.save(Advertisement.builder()
                    .id(id)
                    .title(advertisementDTO.getTitle())
                    .description(advertisementDTO.getDescription())
                    .build());
        }, () -> {
            throw new NotFoundEntityByUuid(EntityName.ADVERTISEMENT.getDisplayName(Locale.getDefault()), id.toString());
        });
        // TODO  - uuid correct?
        return advertisementDTO;

    }

    @Transactional
    public void delete(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid(EntityName.ADVERTISEMENT.getDisplayName(Locale.getDefault()), id.toString());
        }
        advertisementRepository.deleteById(id);
    }
}
