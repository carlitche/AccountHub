package com.accounthub.accountweb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateAccountDto(

        @NotNull(message="Customer ID is not valid!")
        @Positive(message = "Customer ID is not valid!")
        Long customerId,
        @NotNull(message="Initial credit is not valid!")
        @PositiveOrZero(message = "Initial credit is not valid!")
        BigDecimal initialCredit
) {}
