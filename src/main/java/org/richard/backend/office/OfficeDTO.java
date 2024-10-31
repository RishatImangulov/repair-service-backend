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
public class OfficeDTO implements Serializable {

    private UUID id;

    @NotNull(message = "Short name is required")
    @Size(min = 2, max = 5, message = "Short name must be between 2 and 5 characters")
    @NotBlank(message = "Short name can't be blank")
    private String shortname;

    @NotNull(message = "Name if required")
    @Size(max = 255, message = "Description can't exceed 255 characters")
    @NotBlank
    private String name;

}