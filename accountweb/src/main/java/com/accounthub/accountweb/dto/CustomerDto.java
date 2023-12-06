package com.accounthub.accountweb.dto;

import java.util.List;

public record CustomerDto(
        Long customerId,
        String name,
        String surname,
        String email,
        String phone,
        List<AccountDto> accounts
) {}
