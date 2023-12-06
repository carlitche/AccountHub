package com.accounthub.accountweb.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDto(
        @NotBlank(message = "Name is empty")
        String name,
        @NotBlank(message = "Surname is empty")
        String surname,
        @NotBlank(message = "Email is empty")
        String email,
        @NotBlank(message = "Phone is empty")
        String phone
) {}
