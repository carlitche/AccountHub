package com.accounthub.bankaccount.endpoint;

import com.accounthub.bankaccount.dto.CreateCustomerDto;
import com.accounthub.bankaccount.dto.CustomerDto;
import com.accounthub.bankaccount.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    static final String BASE_URL = "/api/customers";

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity newCustomer(@Valid @RequestBody CreateCustomerDto customerDto, UriComponentsBuilder ucb){
        log.info("Create New Customer: {}", customerDto);
        Long customerId = customerService.createCustomer(customerDto);

        URI locationOfNewAccount = ucb.path(BASE_URL + "/{customerId}")
                .buildAndExpand(customerId)
                .toUri();
        return ResponseEntity.created(locationOfNewAccount).build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId){
        log.info("Get  Customer with ID: {}", customerId);
        CustomerDto customerDto = customerService.getCustomer(customerId);

        return ResponseEntity.ok(customerDto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        log.info("Get All Customer list: {}");
        List<CustomerDto> customerDtos = customerService.getAllCustomer();

        return ResponseEntity.ok(customerDtos);
    }
}
