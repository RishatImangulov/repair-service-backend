package org.richard.backend.person;

import lombok.Builder;
import lombok.Value;
import org.richard.backend.advertisement.AdvertisementDTO;
import org.richard.backend.clientStatus.ClientStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */

@Builder
@Value
public class PersonResponseDTO implements Serializable {

    UUID id;

    String fullName;

    String phonePrimary;

    String phoneSecondary;

    String email;

    String telegram;

    AdvertisementDTO advertisementDTO;

    ClientStatus clientStatus;
}