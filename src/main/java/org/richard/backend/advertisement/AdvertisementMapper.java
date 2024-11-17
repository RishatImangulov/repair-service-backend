package org.richard.backend.advertisement;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper extends Converter<Advertisement, AdvertisementDTO> {

    AdvertisementDTO convert(Advertisement advertisement);

    @InheritInverseConfiguration
    @DelegatingConverter
    Advertisement invertConvert(AdvertisementDTO advertisementDTO);
// TODO useless method...
    void updateEntityFromDTO(@NonNull AdvertisementDTO dto, @MappingTarget Advertisement entity);
}