package org.richard.backend.person;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.richard.backend.advertisement.AdvertisementMapper;

import java.util.List;

@Mapper(uses = { AdvertisementMapper.class }, componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "advertisement.id", target = "advertisementId")
    PersonResponseDTO toResponseDTO(Person person);

    @Mapping(source = "advertisement", target = "advertisement")
    Person toEntity(PersonRequestDTO personRequestDTO);

    @Mapping(source = "advertisement.id", target = "advertisement")
    PersonRequestDTO toRequestDTO(Person person);

//    List<Person> toEntityList(List<PersonRequestDTO> personRequestDTOList);
    List<PersonResponseDTO> toDTOList(List<Person> personList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO( PersonRequestDTO requestDTO, @MappingTarget Person person);
}