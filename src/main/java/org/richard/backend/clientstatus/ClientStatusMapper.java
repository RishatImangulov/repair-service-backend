package org.richard.backend.clientstatus;

import org.richard.backend.exception.NullDataMappingException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientStatusMapper {

    public ClientStatus toEntity(ClientStatusDTO clientStatusDTO) {
        if (clientStatusDTO == null) {
            throw new NullDataMappingException("Cannot map NULL ClientStatusDTO to ClientStatus entity.");
        }

        return ClientStatus.builder()
                .id(clientStatusDTO.getId())
                .title(clientStatusDTO.getTitle())
                .description(clientStatusDTO.getDescription())
                .build();
    }

    public ClientStatusDTO toDTO(ClientStatus clientStatus) {
        if (clientStatus == null) {
            throw new NullDataMappingException("Cannot map NULL ClientStatus entity to ClientStatusDTO.");
        }

        return ClientStatusDTO.builder()
                .id(clientStatus.getId())
                .title(clientStatus.getTitle())
                .description(clientStatus.getDescription())
                .build();
    }

    public List<ClientStatusDTO> toDTOList(List<ClientStatus> clientStatuses) {
        return clientStatuses.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClientStatus> toEntityList(List<ClientStatusDTO> clientStatusDTOs) {
        return clientStatusDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public ClientStatus updateEntityFromDTO(ClientStatus existingStatus, ClientStatusDTO clientStatusDTO) {
        if (clientStatusDTO.getTitle() != null && !clientStatusDTO.getTitle().isBlank()) {
            existingStatus.setTitle(clientStatusDTO.getTitle());
        }

        if (clientStatusDTO.getDescription() != null) {
            existingStatus.setDescription(clientStatusDTO.getDescription());
        }
        return existingStatus;
    }
}
