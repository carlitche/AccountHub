package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.CreateCustomerDto;
import com.accounthub.bankaccount.dto.CustomerDto;
import com.accounthub.bankaccount.entity.Account;
import com.accounthub.bankaccount.entity.Customer;
import com.accounthub.bankaccount.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CustomerServiceImpl.class)
@ActiveProfiles("test")
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void create_new_customer(){
        Long customerId = 1l;
        Customer customer = Customer.builder()
                .customerId(customerId)
                .name("John")
                .surname("Doe")
                .email("john.doe@mail.com")
                .phone("+3699999999")
                .build();

        CreateCustomerDto customerDto = new CreateCustomerDto("John", "Doe", "john.doe@mail.com", "+3699999999");

        when(customerRepository.save(any())).thenReturn(customer);

        Long newCustomerId = customerService.createCustomer(customerDto);

        assertThat(newCustomerId).isNotNull();
        assertThat(newCustomerId).isEqualTo(customerId);
    }

    @Test
    void get_customer_by_id(){
        Long customerId = 1l;
        List<Account> accounts = List.of(
                Account.builder()
                        .accountId(1L)
                        .balance(new BigDecimal("10.00"))
                        .createdAt(LocalDateTime.now())
                        .type("Current Account")
                        .build()
        );
        Customer customer = Customer.builder()
                .customerId(customerId)
                .name("John")
                .surname("Doe")
                .email("john.doe@mail.com")
                .phone("+3699999999")
                .accounts(null)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(customer));

        CustomerDto customerDto = customerService.getCustomer(customerId);

        assertThat(customerDto).isNotNull();
        assertThat(customerDto.name()).isEqualTo(customer.getName());
        assertThat(customerDto.customerId()).isEqualTo(customer.getCustomerId());
    }
}
