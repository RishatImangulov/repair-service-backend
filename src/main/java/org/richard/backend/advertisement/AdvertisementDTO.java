package org.richard.backend.advertisement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private UUID id;

    @NotBlank(message = "Title can't be blank")
    @Size(max = 64, min = 2, message = "Title must be between 2 and 64 characters")
    private String title;

    @NotNull(message = "Description is required")
    @Size(max = 255, message = "Description can't exceed 255 characters")
    private String description;
}
