package com.accounthub.accountweb.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AccountDto(
    Long accountId,

    BigDecimal balance,

    String type,

    LocalDateTime createdAt,

    Long ownerId,

    List<TransactionDto> transactions
) {}

