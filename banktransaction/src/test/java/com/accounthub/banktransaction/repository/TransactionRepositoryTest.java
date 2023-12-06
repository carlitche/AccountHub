package com.accounthub.banktransaction.repository;

import com.accounthub.banktransaction.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void context(){

    }

    @Test
    void create_new_transaction(){
        Transaction transaction = Transaction.builder()
                                            .accountId(1L)
                                            .amount(new BigDecimal("20.20"))
                                            .createdAt(LocalDateTime.now())
                                            .description("Initial Deposit")
                                            .build();

        Transaction newTransaction = transactionRepository.save(transaction);

        assertThat(newTransaction).isNotNull();

    }

    @Test
    @Sql(scripts = "/insert.sql")
    void get_transaction_by_account_id(){
        Long accountId = 1L;

        Optional<List<Transaction>> transaction = transactionRepository.findByAccountId(accountId);

        assertThat(transaction).isNotEmpty();
        assertThat(transaction.get()).hasSize(2);
        assertThat(transaction.get().stream().findFirst().get().getAccountId()).isEqualTo(accountId);
    }
}
