package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.CreateCustomerDto;
import com.accounthub.bankaccount.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    Long createCustomer(CreateCustomerDto customerDto);

    CustomerDto getCustomer(Long customerId);

    List<CustomerDto> getAllCustomer();
}
