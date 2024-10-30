package org.richard.backend.person;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */
@Value
public class PersonDto implements Serializable {
    UUID id;

    @NotNull
    @Size(max = 255)
    String name;

    @NotNull
    Long phone;

    UUID advertisementId;

    String advertisementTitle;

    String advertisementDescription;

    @NotNull
    @Size(max = 128)
    String clientStatusId;
}