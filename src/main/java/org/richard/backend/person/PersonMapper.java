package org.richard.backend.person;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.richard.backend.advertisement.Advertisement;
import org.richard.backend.advertisement.AdvertisementDTO;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "advertisement" , target = "advertisement")
    Person toEntity(PersonRequestDTO dto, @Context Advertisement advertisement);

    @Mapping(source = "advertisement.id" , target = "advertisementId")
    PersonResponseDTO toResponseDTO(Person person);

    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(PersonRequestDTO dto, @MappingTarget Person person);

}
