package org.richard.backend.person;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.advertisement.AdvertisementDTO;
import org.richard.backend.advertisement.AdvertisementRepository;
import org.richard.backend.exception.DuplicateTitleException;
import org.richard.backend.exception.NotFoundEntityByUuid;
import org.richard.backend.exception.TitleIsBlank;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AdvertisementRepository advertisementRepository;
    private final PersonMapper personMapper;


    public List<PersonResponseDTO> getAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toResponseDTO)
                .toList();
    }

    public List<PersonResponseDTO> findAllByTitleFragment(String titleFragment) {
        return personRepository.findByFullNameContainingIgnoreCase(titleFragment)
                .stream()
                .map(personMapper::toResponseDTO)
                .toList();
    }

    public PersonResponseDTO getById(UUID id) {
        return personRepository.findById(id)
                .map(personMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundEntityByUuid("Person", id.toString()));
    }

    @Transactional
    public PersonResponseDTO create (PersonCreateDTO personCreateDTO) {
        var advertisement = advertisementRepository.findById(personCreateDTO.getAdvertisement())
                .orElseThrow(() -> new NotFoundEntityByUuid("Advertisement", personCreateDTO.getAdvertisement().toString()));

        personCreateDTO.setFullName(formatFullName(personCreateDTO.getFullName()));

        var person = personMapper.toEntity(personCreateDTO, advertisement);
        person = personRepository.save(person);

        return personMapper.toResponseDTO(person);
    }

    private String formatFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return fullName;
        }
        return Arrays.stream(fullName.trim().split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }


    @Transactional
    public PersonResponseDTO update (UUID id , PersonCreateDTO personCreateDTO) {

    }

    @Transactional
    public AdvertisementDTO update(UUID id, AdvertisementDTO advertisementDTO) {

        advertisementDTO.setTitle(advertisementDTO.getTitle().trim());
        advertisementDTO.setDescription(advertisementDTO.getDescription().trim());

        advertisementRepository.findById(id).ifPresentOrElse(existingAd -> {
            if (!existingAd.getTitle().equalsIgnoreCase(advertisementDTO.getTitle())) {
                if (advertisementRepository.existsByTitleIgnoreCase(advertisementDTO.getTitle())) {
                    throw new DuplicateTitleException("Advertisement", "Title already exists");
                }
            }
            // TODO why it is necessary?
            advertisementDTO.setId(id); // Make sure the id stays consistent
            advertisementRepository.save(advertisementMapper.updateEntityFromDTO(existingAd, advertisementDTO));
        }, () -> {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        });
        // TODO  - uuid correct?
        return advertisementDTO;

    }

    @Transactional
    public void delete(UUID id) {
        if (!advertisementRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("Advertisement", id.toString());
        }
        advertisementRepository.deleteById(id);
    }

}
