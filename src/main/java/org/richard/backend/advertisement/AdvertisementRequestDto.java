package org.richard.backend.advertisement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * DTO for {@link Advertisement}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdvertisementRequestDto {
    @NotNull
    @Size(min = 2, max = 255)
    @NotBlank
    private String title;
    @NotNull(message = "title can't be Null")
    @Size(message = "description can't be blank", max = 255)
    @NotBlank
    private String description;
}