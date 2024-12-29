package org.richard.backend.person;

import org.mapstruct.Mapper;
import org.richard.backend.advertisement.Advertisement;
import org.richard.backend.advertisement.AdvertisementRequestDto;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PersonMapper extends Converter<Person, PersonResponseDTO> {
    //ToDo another worry
    PersonResponseDTO convert(Person person);

    Advertisement toEntity(AdvertisementRequestDto advertisementRequestDto);

    AdvertisementRequestDto toAdvertisementRequestDto(Advertisement advertisement);
}
