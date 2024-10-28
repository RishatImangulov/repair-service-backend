package org.richard.backend.office;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Office}
 */
@Value
public class OfficeDTO implements Serializable {

    UUID id;
    @NotNull
    @Size(min = 2, max = 5)
    @NotBlank
    String shortname;

    @NotNull
    @Size(max = 255)
    @NotBlank
    String name;
}