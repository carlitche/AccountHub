package com.accounthub.bankaccount.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionDto(
        Long accountId,
        BigDecimal amount,
        BigDecimal balance,
        LocalDateTime createdAt,
        String description
) {}
