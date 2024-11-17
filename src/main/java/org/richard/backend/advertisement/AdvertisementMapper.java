package org.richard.backend.advertisement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.richard.backend.advertisement.Advertisement;
import org.richard.backend.advertisement.AdvertisementDTO;

@Mapper
public interface AdvertisementMapper {

    AdvertisementDTO toDTO(Advertisement advertisement);

    Advertisement toEntity(AdvertisementDTO advertisementDTO);

}