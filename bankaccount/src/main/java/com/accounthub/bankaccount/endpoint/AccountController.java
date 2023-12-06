package com.accounthub.bankaccount.endpoint;

import com.accounthub.bankaccount.dto.CreateAccountDto;
import com.accounthub.bankaccount.dto.InfoAccountDto;
import com.accounthub.bankaccount.service.AccountService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping(AccountController.BASE_URL)
public class AccountController {
    static final String BASE_URL = "/api/accounts";

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<InfoAccountDto> getAccountById(@PathVariable("accountId") Long accountId){
        log.info("Get account with id: {}", accountId);
        InfoAccountDto account= accountService.findAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity newAccount(@Valid @RequestBody CreateAccountDto newAccount, UriComponentsBuilder ucb){
        log.info("Create new account: {}", newAccount);

        Long accountId = accountService.createAccount(newAccount);

        URI locationOfNewAccount = ucb.path(BASE_URL + "/{accountId}")
                .buildAndExpand(accountId)
                .toUri();
        return ResponseEntity.created(locationOfNewAccount).build();
    }
}
