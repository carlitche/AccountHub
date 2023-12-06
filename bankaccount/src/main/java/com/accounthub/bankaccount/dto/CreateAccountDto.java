package com.accounthub.bankaccount.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountDto(
        @NotNull
        Long customerId,
        @NotNull
        BigDecimal initialCredit
) {}
