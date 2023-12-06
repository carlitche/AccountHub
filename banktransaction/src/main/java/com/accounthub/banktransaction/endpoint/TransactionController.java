package com.accounthub.banktransaction.endpoint;

import com.accounthub.banktransaction.dto.CreateTransactionDto;
import com.accounthub.banktransaction.entity.Transaction;
import com.accounthub.banktransaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(TransactionController.BASE_URL)
public class TransactionController {
    static final String BASE_URL = "/api/transactions";

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    ResponseEntity createNewTransaction(@RequestBody CreateTransactionDto transactionDto, UriComponentsBuilder ucb){
        log.info("Create New Transaction: {}", transactionDto);
        Long newTransaction = transactionService.createNewTransaction(transactionDto);

        URI locationOfNewTransaction = ucb.path(BASE_URL + "/{transactionId}")
                .buildAndExpand(newTransaction)
                .toUri();

        return ResponseEntity.created(locationOfNewTransaction).build();
    }

    @GetMapping
    ResponseEntity<List<Transaction>> getListTransactionByAccountId(@RequestParam("accountId") Long accountId){
        log.info("Get Transaction List from account ID: {}", accountId);
        List<Transaction> transactionList = transactionService.getListTransactionByAccountId(accountId);

        return ResponseEntity.ok(transactionList);
    }

}
