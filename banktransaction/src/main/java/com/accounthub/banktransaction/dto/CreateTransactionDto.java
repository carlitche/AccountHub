package com.accounthub.banktransaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionDto(
        Long accountId,
        BigDecimal amount,
        BigDecimal balance,
        LocalDateTime createdAt,
        String description
) {}
