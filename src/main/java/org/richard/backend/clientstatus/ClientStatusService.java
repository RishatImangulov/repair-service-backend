package org.richard.backend.clientstatus;

import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientStatusService {

    private final ClientStatusRepository clientStatusRepository;
    private final ClientStatusMapper clientStatusMapper;

    public ClientStatusService(ClientStatusRepository clientStatusRepository, ClientStatusMapper clientStatusMapper) {
        this.clientStatusRepository = clientStatusRepository;
        this.clientStatusMapper = clientStatusMapper;
    }

    public List<ClientStatusDTO> getClientStatuses() {
        return clientStatusMapper.toDTOList(
                clientStatusRepository.findAll());
    }

    public List<ClientStatusDTO> searchClientStatusesByTitleFragment(String titleFragment) {
        return clientStatusMapper.toDTOList(
                clientStatusRepository.findByTitleContainingIgnoreCase(titleFragment));
    }

    public ClientStatusDTO getClientStatusById(UUID id) {
        return clientStatusRepository.findById(id)
                .map(clientStatusMapper::toDTO)
                .orElseThrow(() -> new NotFoundEntityByUuid("ClientStatus", id.toString()));
    }

    @Transactional
    public void createClientStatus(ClientStatusDTO clientStatusDTO) {
        if (clientStatusDTO.getTitle() == null || clientStatusDTO.getTitle().isBlank()) {
            throw new TitleIsBlank("ClientStatus");
        }

        clientStatusDTO.setTitle(clientStatusDTO.getTitle().trim());
        clientStatusDTO.setDescription(clientStatusDTO.getDescription().trim());

        if (clientStatusRepository.existsByTitleIgnoreCase(clientStatusDTO.getTitle())) {
            throw new DuplicateTitleException("ClientStatus", "Title already exists");
        }

        clientStatusRepository.save(clientStatusMapper.toEntity(clientStatusDTO));
    }

    @Transactional
    public void updateClientStatus(UUID id, ClientStatusDTO clientStatusDTO) {
        clientStatusDTO.setTitle(clientStatusDTO.getTitle().trim());
        clientStatusDTO.setDescription(clientStatusDTO.getDescription().trim());

        clientStatusRepository.findById(id).ifPresentOrElse(existingStatus -> {
            if (!existingStatus.getTitle().equalsIgnoreCase(clientStatusDTO.getTitle())) {
                if (clientStatusRepository.existsByTitleIgnoreCase(clientStatusDTO.getTitle())) {
                    throw new DuplicateTitleException("ClientStatus", "Title already exists");
                }
            }
            clientStatusDTO.setId(id); // Ensure ID consistency
            clientStatusRepository.save(clientStatusMapper.updateEntityFromDTO(existingStatus, clientStatusDTO));
        }, () -> {
            throw new NotFoundEntityByUuid("ClientStatus", id.toString());
        });
    }

    @Transactional
    public void deleteClientStatus(UUID id) {
        if (!clientStatusRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("ClientStatus", id.toString());
        }
        clientStatusRepository.deleteById(id);
    }
}
