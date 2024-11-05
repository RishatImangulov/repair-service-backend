package org.richard.backend.office;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.entity.EntityName;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;

    public List<OfficeDTO> getAll() {
        return officeMapper.toDTOList(
                officeRepository.findAll());
    }

    // should I check blank fragment?
    public List<OfficeDTO> findAllByTitleFragment(String titleFragment) {
        return officeMapper.toDTOList(
                officeRepository.findByShortnameContainingIgnoreCase(titleFragment));
    }

    public OfficeDTO getById(UUID id) {
        return officeRepository.findById(id)
                .map(officeMapper::toDTO)
                .orElseThrow(() -> new NotFoundEntityByUuid(
                        EntityName.OFFICE.getDisplayName(Locale.forLanguageTag("ru")),
                        id.toString()));

    }

    @Transactional
    public OfficeDTO create(OfficeDTO officeDTO) {
        if (officeDTO.getShortname() == null || officeDTO.getShortname().isBlank()) {
            throw new TitleIsBlank(EntityName.OFFICE.getDisplayName(Locale.forLanguageTag("ru")));
        }

        officeDTO.setId(null); // because we are creating
        officeDTO.setShortname(officeDTO.getShortname().trim());
        officeDTO.setName(officeDTO.getName().trim());


        if (officeRepository.existsByShortnameIgnoreCase(officeDTO.getShortname())) {
            throw new DuplicateTitleException(EntityName.OFFICE.getDisplayName(Locale.forLanguageTag("ru")),
                    Locale.forLanguageTag("ru"));
        }

        var saved = officeRepository.save(officeMapper.toEntity(officeDTO));
        return officeMapper.toDTO(saved);
    }

    @Transactional
    public OfficeDTO update(UUID id, OfficeDTO officeDTO) {

        officeDTO.setShortname(officeDTO.getShortname().trim());
        officeDTO.setName(officeDTO.getName().trim());

        officeRepository.findById(id).ifPresentOrElse(existingAd -> {
            if (!existingAd.getShortname().equalsIgnoreCase(officeDTO.getShortname())) {
                if (officeRepository.existsByShortnameIgnoreCase(officeDTO.getShortname())) {
                    throw new DuplicateTitleException(EntityName.OFFICE.getDisplayName(Locale.forLanguageTag("ru")),
                            Locale.forLanguageTag("ru"));
                }
            }
            // TODO why it is necessary?
            officeDTO.setId(id); // Make sure the id stays consistent
            officeRepository.save(officeMapper.updateEntityFromDTO(existingAd, officeDTO));
        }, () -> {
            throw new NotFoundEntityByUuid(EntityName.OFFICE.getDisplayName(Locale.forLanguageTag("ru")), id.toString());
        });
        // TODO  - uuid correct?
        return officeDTO;

    }

    @Transactional
    public void delete(UUID id) {
        if (!officeRepository.existsById(id)) {
            throw new NotFoundEntityByUuid(EntityName.OFFICE.getDisplayName(Locale.forLanguageTag("ru")), id.toString());
        }
        officeRepository.deleteById(id);
    }
}
