package com.accounthub.bankaccount.config;

import com.accounthub.bankaccount.entity.Customer;
import com.accounthub.bankaccount.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("!test")
@Component
public class MyRunner implements CommandLineRunner {

    CustomerRepository customerRepository;

    public MyRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        customerRepository.save(Customer.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@gmail.com")
                .phone("+3169999999")
                .build()
        );
    }
}
