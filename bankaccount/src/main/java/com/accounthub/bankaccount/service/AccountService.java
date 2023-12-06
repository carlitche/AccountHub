package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.AccountDto;
import com.accounthub.bankaccount.dto.CreateAccountDto;
import com.accounthub.bankaccount.dto.InfoAccountDto;

public interface AccountService {

    InfoAccountDto findAccountById(Long accountId);

    Long createAccount(CreateAccountDto createAccountDto);
}
