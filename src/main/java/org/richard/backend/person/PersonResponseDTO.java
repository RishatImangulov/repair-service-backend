package org.richard.backend.person;

import lombok.Builder;
import lombok.Value;

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

    UUID advertisementId;

    String clientStatus;
}