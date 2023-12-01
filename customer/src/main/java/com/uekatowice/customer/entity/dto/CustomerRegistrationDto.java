package com.uekatowice.customer.entity.dto;

public record CustomerRegistrationDto(
        String firstName,
        String lastName,
        String email
) {
}