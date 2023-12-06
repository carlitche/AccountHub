package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.AccountDto;
import com.accounthub.bankaccount.dto.CreateCustomerDto;
import com.accounthub.bankaccount.dto.CustomerDto;
import com.accounthub.bankaccount.entity.Customer;
import com.accounthub.bankaccount.exception.NotFoundException;
import com.accounthub.bankaccount.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Long createCustomer(CreateCustomerDto customerDto) {
        Customer customer = Customer.builder()
                .name(customerDto.name())
                .surname(customerDto.surname())
                .email(customerDto.email())
                .phone(customerDto.phone())
                .build();

        return customerRepository.save(customer).getCustomerId();
    }

    @Override
    public CustomerDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer Not Found"));
        List<AccountDto> accounts = new ArrayList<>();

        if (customer.getAccounts() != null && !customer.getAccounts().isEmpty()) {
            accounts = customer.getAccounts().stream().map(account -> new AccountDto(account.getAccountId(),
                                                                                           account.getBalance(),
                                                                                           account.getType(),
                                                                                           account.getCreatedAt(),
                                                                                           customerId, null)
                                                                           ).toList();
        }


        return new CustomerDto( customer.getCustomerId(),
                customer.getName(),
                customer.getSurname(),
                customer.getEmail(),
                customer.getPhone(),
                accounts);
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        if(!customerList.isEmpty()){
            customerDtos = customerList.stream().map(customer -> new CustomerDto(customer.getCustomerId(),
                    customer.getName(),
                    customer.getSurname(),
                    customer.getEmail(),
                    customer.getPhone(), null)).toList();

        }
        return customerDtos;
    }
}
