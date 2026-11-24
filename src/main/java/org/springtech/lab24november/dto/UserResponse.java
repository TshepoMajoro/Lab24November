package org.springtech.lab24november.dto;

public record UserResponse (
        long id,
        String name,
        String email
) {
}
