package com.accounthub.bankaccount.repository;

import com.accounthub.bankaccount.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Sql(scripts = "/insert.sql")
    void find_customer_by_id(){
        Long customerId = 1L;
        Optional<Customer> customer = customerRepository.findById(customerId);

        assertThat(customer).isNotEmpty();
        assertThat(customer.get().getCustomerId()).isEqualTo(customerId);
    }

    @Test
    void customer_id_do_not_exist(){

        List<Customer> list = customerRepository.findAll();
        list.stream().forEach(System.out::println);

        Long customerId = 99L;
        Optional<Customer> customer = customerRepository.findById(customerId);

        assertThat(customer).isEmpty();
    }

    @Test
    void create_new_customer(){
        Customer customer = Customer.builder()
                .name("Maria")
                .surname("Doe")
                .email("maria.doe@mail.com")
                .phone("+31699999999")
                .build();

        Customer newCustomer = customerRepository.save(customer);

        assertThat(newCustomer).isNotNull();

        assertThat(newCustomer.getName()).isEqualTo(customer.getName());
    }



}
