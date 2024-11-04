package org.richard.backend.person;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.richard.backend.advertisement.Advertisement;
import org.richard.backend.advertisement.AdvertisementDTO;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "advertisement" , target = "advertisement")
    Person toEntity(PersonCreateDTO dto, @Context Advertisement advertisement);

    @Mapping(source = "advertisement.id" , target = "advertisementId")
    PersonResponseDTO toResponseDTO(Person person);

    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);

}
