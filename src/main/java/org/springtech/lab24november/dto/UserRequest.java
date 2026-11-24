package org.springtech.lab24november.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull(message = "name is a required field.")
        @Size(min = 5, max = 20, message = "a name must be at least 5 characters long and at most 20 characters long")
        String name,
        @NotNull(message = "email is a required field.")
        @Email(message = "You must provide a valid email address.")
        String email
) {
}
