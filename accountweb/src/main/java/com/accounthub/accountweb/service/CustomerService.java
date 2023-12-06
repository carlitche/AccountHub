package com.accounthub.accountweb.service;

import com.accounthub.accountweb.dto.CreateCustomerDto;
import com.accounthub.accountweb.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@HttpExchange("/api/customers")
public interface CustomerService {

    @PostExchange()
    public ResponseEntity newCustomer(@RequestBody CreateCustomerDto customerDto);

    @GetExchange("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId);

    @GetExchange()
    public ResponseEntity<List<CustomerDto>> getAllCustomers();

}
