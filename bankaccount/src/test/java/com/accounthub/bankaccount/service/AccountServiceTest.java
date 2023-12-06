package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.CreateAccountDto;
import com.accounthub.bankaccount.dto.CreateTransactionDto;
import com.accounthub.bankaccount.entity.Account;
import com.accounthub.bankaccount.entity.Customer;
import com.accounthub.bankaccount.repository.AccountRepository;
import com.accounthub.bankaccount.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AccountServiceImpl.class)
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    TransactionService transactionService;

    Customer customer;

    Account account;

    @BeforeEach
    void setup(){
        customer = Customer.builder()
                .customerId(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@email.com")
                .phone("+3162728399")
                .build();

        account = Account.builder()
                .balance(new BigDecimal("20.45"))
                .type("Saving Account")
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();
    }

    @Test
    void create_new_account(){
        Long customerId = 1L;
        BigDecimal initialCredit = new BigDecimal("20.45");
        Account newAccount = Account.builder()
                .accountId(1L)
                .balance(new BigDecimal("20.45"))
                .type("Saving Account")
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any())).thenReturn(newAccount);

        Long accountId = accountService.createAccount(new CreateAccountDto(customerId, initialCredit));

        ArgumentCaptor<CreateTransactionDto> transactionCaptured = ArgumentCaptor.forClass(CreateTransactionDto.class);
        verify(transactionService, times(1)).createTransaction(transactionCaptured.capture());
        CreateTransactionDto transactionDto = transactionCaptured.getValue();

        assertThat(transactionDto.amount()).isEqualTo(initialCredit);
    }
}
