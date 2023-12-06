package com.accounthub.bankaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AccountDto (
    Long accountId,

    BigDecimal balance,

    String type,

    LocalDateTime createdAt,

    Long ownerId,

    List<TransactionDto> transactions
) {}

