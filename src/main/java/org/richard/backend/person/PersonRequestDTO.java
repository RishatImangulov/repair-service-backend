package org.richard.backend.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.richard.backend.clientStatus.ClientStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */

@Builder
@Data
public class PersonRequestDTO implements Serializable {

//    UUID id;

    @NotNull
    @Size(min = 2, max = 255)
    String fullName;

    @NotNull
    @NotBlank(message = "Primary phone number is required.")
    @Pattern(
            regexp = "^[+][0-9]{1,3}[0-9]{7,14}$",
            message = "Invalid phone number format. Expected format: +1234567890"
    )
    @Size(max = 15)
    String phonePrimary;

    @NotBlank(message = "Primary phone number is required.")
    @Pattern(
            regexp = "^[+][0-9]{1,3}[0-9]{7,14}$",
            message = "Invalid phone number format. Expected format: +1234567890"
    )
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
    ClientStatus clientStatus;
}