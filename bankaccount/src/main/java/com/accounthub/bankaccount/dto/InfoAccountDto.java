package com.accounthub.bankaccount.dto;

import java.math.BigDecimal;
import java.util.List;

public record InfoAccountDto(
        String name,
        String surname,
        BigDecimal balance,
        List<TransactionDto> transactionList
) {}
