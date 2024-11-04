package org.richard.backend.person;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */

@Builder
@Data
public class PersonCreateDTO implements Serializable {
    @NotNull
    @Size(max = 255)
    String fullName;

    @NotNull
    @Size(max = 15)
    String phonePrimary;

    @Size(max = 15)
    String phoneSecondary;

    @Size(max = 100)
    String email;

    @Size(max = 200)
    String telegram;

    @NotNull
    UUID advertisement;

    @NotNull
    @Size(max = 128)
    String clientStatus;
}