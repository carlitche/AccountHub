package com.accounthub.bankaccount.repository;

import com.accounthub.bankaccount.entity.Account;
import com.accounthub.bankaccount.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Sql(scripts = "/insert.sql")
    void create_new_account(){

        Long customerId = 1L;

        Optional<Customer> customer = customerRepository.findById(customerId);
        assertThat(customer).isNotEmpty();
        assertThat(customer.get().getCustomerId()).isEqualTo(customerId);

        Account account = Account.builder()
                .balance(new BigDecimal("20.45"))
                .type("Current Account")
                .createdAt(LocalDateTime.now())
                .customer(customer.get())
                .build();

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount).isNotNull();
    }

}
