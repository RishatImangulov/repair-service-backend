package org.richard.backend.advertisement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


/**
 * DTO for {@link Advertisement}
 */
@Builder
@Data
@NoArgsConstructor
public class AdvertisementDTO implements Serializable {

    private UUID id;

    @NotNull
    @NotBlank(message = "Title can't be blank")
    @Size(max = 64, min = 2, message = "Title must be between 2 and 64 characters")
    private String title;

    @NotNull(message = "Description is required")
    @Size(max = 255, message = "Description can't exceed 255 characters")
    private String description;
}
