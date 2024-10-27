package org.richard.backend.clientstatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ClientStatus}
 */
@Value
public class ClientStatusDTO implements Serializable {
    UUID id;

    @NotNull
    @Size(max = 64)
    @NotBlank
    String title;

    @NotNull
    @Size(max = 255)
    String description;
}