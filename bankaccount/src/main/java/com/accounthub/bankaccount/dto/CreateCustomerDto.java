package com.accounthub.bankaccount.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerDto(
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        String email,
        @NotBlank
        String phone
) {
}
