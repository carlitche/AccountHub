package com.accounthub.bankaccount.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        Long transactionId,
        BigDecimal amount,
        BigDecimal balance,
        LocalDateTime createdAt,
        String description
) {}
