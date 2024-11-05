package org.richard.backend.advertisement;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Mapper (componentModel = "spring")
public abstract class AdvertisementMapper {

    private final AdvertisementService advertisementService;

    abstract AdvertisementDTO toDTO(Advertisement advertisement);

    abstract Advertisement toEntity(AdvertisementDTO advertisementDTO);

    abstract List<Advertisement> toEntityList(List<AdvertisementDTO> advertisementDTOList);

    abstract List<AdvertisementDTO> toDTOList(List<Advertisement> advertisementList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void updateEntityFromDTO( AdvertisementDTO advertisementDTO, @MappingTarget Advertisement advertisement);

    Advertisement map(UUID id) {
        return toEntity(advertisementService.getById(id));
    }
}


//
//
//    public Advertisement toEntity(AdvertisementDTO advertisementDTO) {
//        if (advertisementDTO == null) {
//            throw new NullDataMappingException("Cannot map NULL AdvertisementDTO to Advertisement entity.");
//        }
//
//        return Advertisement.builder()
//                .id(advertisementDTO.getId())
//                .title(advertisementDTO.getTitle())
//                .description(advertisementDTO.getDescription())
//                .build();
//    }
//
//    public AdvertisementDTO toDTO(Advertisement advertisement) {
//        if (advertisement == null) {
//            throw new NullDataMappingException("Cannot map NULL Advertisement entity to AdvertisementDTO.");
//        }
//
//        return AdvertisementDTO.builder()
//                .id(advertisement.getId())
//                .title(advertisement.getTitle())
//                .description(advertisement.getDescription())
//                .build();
//    }
//
//    public List<AdvertisementDTO> toDTOList(List<Advertisement> advertisements) {
//        return advertisements.stream()
//                .map(this::toDTO)
//                .toList();
//    }
//
//    public List<Advertisement> toEntityList(List<AdvertisementDTO> advertisementDTOs) {
//        return advertisementDTOs.stream()
//                .map(this::toEntity)
//                .toList();
//    }
//
//    public Advertisement updateEntityFromDTO(Advertisement existingAd, AdvertisementDTO advertisementDTO) {
//        if (advertisementDTO.getTitle() != null && !advertisementDTO.getTitle().isBlank()) {
//            existingAd.setTitle(advertisementDTO.getTitle());
//        }
//
//        if (advertisementDTO.getDescription() != null) {
//            existingAd.setDescription(advertisementDTO.getDescription());
//        }
//        return existingAd;
//    }

