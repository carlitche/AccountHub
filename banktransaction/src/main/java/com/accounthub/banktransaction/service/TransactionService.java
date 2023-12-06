package com.accounthub.banktransaction.service;

import com.accounthub.banktransaction.dto.CreateTransactionDto;
import com.accounthub.banktransaction.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Long createNewTransaction(CreateTransactionDto transactionDto);

    List<Transaction> getListTransactionByAccountId(Long accountId);
}
