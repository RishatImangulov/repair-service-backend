package org.richard.backend.office;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Office}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDTO implements Serializable {

    private UUID id;

    @NotNull
    @Size(min = 2, max = 5)
    @NotBlank
    private String shortname;

    @NotNull
    @Size(max = 255)
    @NotBlank
    private String name;
}