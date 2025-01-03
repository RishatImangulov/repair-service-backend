package org.richard.backend.person;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.richard.backend.advertisement.AdvertisementRepository;
import org.richard.backend.exception.DuplicateFullNameException;
import org.richard.backend.exception.NotFoundEntityByUuid;
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
                .map(personMapper::convert)
                .toList();
    }

    public List<PersonResponseDTO> findAllByTitleFragment(String titleFragment) {
        return personRepository.findByFullNameContainingIgnoreCase(titleFragment)
                .stream()
                .map(personMapper::convert)
                .toList();
    }

    public PersonResponseDTO getById(UUID id) {
        return personRepository.findById(id)
                .map(personMapper::convert)
                .orElseThrow(() -> new NotFoundEntityByUuid("Person", id.toString()));
    }

    @Transactional
    public PersonResponseDTO create(PersonRequestDTO personRequestDTO) {
        var advertisement = advertisementRepository.findById(personRequestDTO.getAdvertisementDTO().getId())
                .orElseThrow(() ->
                        new NotFoundEntityByUuid("Advertisement", personRequestDTO.getAdvertisementDTO().toString()));

        personRequestDTO.setFullName(formatFullName(personRequestDTO.getFullName()));

        var person = Person.builder()
                .id(null) // TODO - its ok? or what
                .fullName(personRequestDTO.getFullName())
                .phonePrimary(personRequestDTO.getPhonePrimary())
                .phoneSecondary(personRequestDTO.getPhoneSecondary())
                .email(personRequestDTO.getEmail())
                .telegram(personRequestDTO.getTelegram())
                .advertisement(advertisement)
                .clientStatus(personRequestDTO.getClientStatus())
                .build();

        return personMapper.convert(personRepository.save(person));

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
    public PersonResponseDTO update(UUID id, PersonRequestDTO personRequestDTO) {

        String fullNameFormatted = formatFullName(personRequestDTO.getFullName());

        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundEntityByUuid("Person", id.toString()));

        advertisementRepository.findById(personRequestDTO.getAdvertisementDTO().getId())
                .orElseThrow(() ->
                        new NotFoundEntityByUuid("Advertisement", personRequestDTO.getAdvertisementDTO().toString()));

        if (!existingPerson.getFullName().equals(fullNameFormatted)) {
            checkDuplicateFullName(fullNameFormatted);
        }

//        personRequestDTO.setId(id);
        personRequestDTO.setFullName(fullNameFormatted);

        existingPerson
                .setId(null) // TODO - its ok? or what
                .setFullName(personRequestDTO.getFullName())
                .setPhonePrimary(personRequestDTO.getPhonePrimary())
                .setPhoneSecondary(personRequestDTO.getPhoneSecondary())
                .setEmail(personRequestDTO.getEmail())
                .setTelegram(personRequestDTO.getTelegram())
                .setAdvertisement(advertisement)
                .setClientStatus(personRequestDTO.getClientStatus())
                .setBuild();
                .

        existingPerson = personMapper.updateEntityFromDTO(existingPerson, personRequestDTO);

        Person updatedPerson = personRepository.save(existingPerson);
        return personMapper.convert(updatedPerson);
    }

    private void checkDuplicateFullName(String fullName) {
        if (personRepository.existsByFullNameIgnoreCase(fullName)) {
            throw new DuplicateFullNameException("Person", "FullName already exists");
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundEntityByUuid("Person", id.toString());
        }
        personRepository.deleteById(id);
    }

}
