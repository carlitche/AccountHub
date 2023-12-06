package com.accounthub.accountweb.service;

import com.accounthub.accountweb.dto.CreateAccountDto;
import com.accounthub.accountweb.dto.InfoAccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/accounts")
public interface AccountService {

    @PostExchange()
    public ResponseEntity newAccount(@RequestBody CreateAccountDto newAccount);

    @GetExchange("/{accountId}")
    public ResponseEntity<InfoAccountDto> getAccountById(@PathVariable("accountId") Long accountId);
}
