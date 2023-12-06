package com.accounthub.banktransaction.service;

import com.accounthub.banktransaction.dto.CreateTransactionDto;
import com.accounthub.banktransaction.entity.Transaction;
import com.accounthub.banktransaction.exception.NotFoundException;
import com.accounthub.banktransaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Long createNewTransaction(CreateTransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .accountId(transactionDto.accountId())
                .amount(transactionDto.amount())
                .balance(transactionDto.balance())
                .createdAt(transactionDto.createdAt())
                .description(transactionDto.description())
                .build();
        Transaction newTransaction = transactionRepository.save(transaction);

        return newTransaction.getTransactionId();
    }

    @Override
    public List<Transaction> getListTransactionByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));
    }


}
