package com.accounthub.banktransaction.service;

import com.accounthub.banktransaction.dto.CreateTransactionDto;
import com.accounthub.banktransaction.entity.Transaction;
import com.accounthub.banktransaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TransactionServiceImpl.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @MockBean
    TransactionRepository transactionRepository;

    @Test
    void create_new_transaction(){
        Long transactionId = 1l;

        Transaction newTransaction = Transaction.builder()
                .transactionId(transactionId)
                .accountId(1L)
                .amount(new BigDecimal("20.45"))
                .balance(new BigDecimal("20.45"))
                .createdAt(LocalDateTime.now())
                .description("Initial Deposit")
                .build();

        when(transactionRepository.save(any())).thenReturn(newTransaction);

        CreateTransactionDto transactionDto= new CreateTransactionDto(1L, new BigDecimal("20.20"), new BigDecimal("20.20"), LocalDateTime.now(), "Initial Deposit");
        Long newTransactionId = transactionService.createNewTransaction(transactionDto);

        assertThat(newTransactionId).isNotNull();
        assertThat(newTransactionId).isEqualTo(transactionId);
    }

    @Test
    void get_list_transaction_by_account_id(){
        Long accountId = 1L;

        List<Transaction> transactionList = List.of(
                Transaction.builder()
                        .transactionId(1L)
                        .accountId(accountId)
                        .amount(new BigDecimal("20.45"))
                        .balance(new BigDecimal("20.45"))
                        .createdAt(LocalDateTime.now())
                        .description("Initial Deposit")
                        .build(),
                Transaction.builder()
                        .transactionId(2L)
                        .accountId(accountId)
                        .amount(new BigDecimal("10.00"))
                        .balance(new BigDecimal("10.45"))
                        .createdAt(LocalDateTime.now())
                        .description("Cash withdraw")
                        .build()
                );

        when(transactionRepository.findByAccountId(accountId)).thenReturn(Optional.of(transactionList));

        List<Transaction> resultList = transactionService.getListTransactionByAccountId(accountId);

        assertThat(resultList).isNotEmpty();
        assertThat(resultList).hasSize(2);

    }
}
