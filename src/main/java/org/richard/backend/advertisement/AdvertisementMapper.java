package org.richard.backend.advertisement;

import org.richard.backend.exception.NullDataMappingException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdvertisementMapper {

    public Advertisement toEntity(AdvertisementDTO advertisementDTO) {
        if (advertisementDTO == null) {
            throw new NullDataMappingException("Cannot map NULL AdvertisementDTO to Advertisement entity.");
        }

        return Advertisement.builder()
                .id(advertisementDTO.getId())
                .title(advertisementDTO.getTitle())
                .description(advertisementDTO.getDescription())
                .build();
    }

    public AdvertisementDTO toDTO(Advertisement advertisement) {
        if (advertisement == null) {
            throw new NullDataMappingException("Cannot map NULL Advertisement entity to AdvertisementDTO.");
        }

        return AdvertisementDTO.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .build();
    }

    public List<AdvertisementDTO> toDTOList(List<Advertisement> advertisements) {
        return advertisements.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<Advertisement> toEntityList(List<AdvertisementDTO> advertisementDTOs) {
        return advertisementDTOs.stream()
                .map(this::toEntity)
                .toList();
    }

    public Advertisement updateEntityFromDTO(Advertisement existingAd, AdvertisementDTO advertisementDTO) {
        if (advertisementDTO.getTitle() != null && !advertisementDTO.getTitle().isBlank()) {
            existingAd.setTitle(advertisementDTO.getTitle());
        }

        if (advertisementDTO.getDescription() != null) {
            existingAd.setDescription(advertisementDTO.getDescription());
        }
        return existingAd;
    }
}
