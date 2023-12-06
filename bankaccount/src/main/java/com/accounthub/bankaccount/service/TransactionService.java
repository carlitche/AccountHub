package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.CreateTransactionDto;
import com.accounthub.bankaccount.dto.TransactionDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Optional;

@HttpExchange("/api/transactions")
public interface TransactionService {

    @PostExchange()
    void createTransaction(@RequestBody CreateTransactionDto newTransactionDto);

    @GetExchange()
    Optional<List<TransactionDto>> getAllTransactionFromAccountId(@RequestParam("accountId") Long accountId);

}
