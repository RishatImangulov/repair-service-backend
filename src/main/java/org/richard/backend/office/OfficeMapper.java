package org.richard.backend.office;

import jdk.jfr.Category;
import org.richard.backend.exception.NullDataMappingException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OfficeMapper {

    public Office toEntity(OfficeDTO officeDTO) {
        if (officeDTO == null) {
            throw new NullDataMappingException("Cannot map NULL OfficeDTO to Office entity.");
        }

        return Office.builder()
                .id(officeDTO.getId())
                .shortname(officeDTO.getShortname())
                .name(officeDTO.getName())
                .build();
    }

    public OfficeDTO toDTO(Office office) {
        if (office == null) {
            throw new NullDataMappingException("Cannot map NULL Office to OfficeDTO.");
        }

        return OfficeDTO.builder()
                .id(office.getId())
                .shortname(office.getShortname())
                .name(office.getName())
                .build();
    }

    public List<OfficeDTO> toDTOList(List<Office> offices) {
        return offices.stream()
                .map(this::toDTO)
                .toList();
    }

    public Office updateEntityFromDTO(Office existingOffice, OfficeDTO officeDTO) {
        if (officeDTO.getShortname() != null
                && !officeDTO.getShortname().isBlank()
                && !officeDTO.getName().isBlank()) {
            existingOffice.setShortname(officeDTO.getShortname());
        }
        if (officeDTO.getName() != null) {
            existingOffice.setName(officeDTO.getName());
        }
        return existingOffice;
    }
}
