package com.accounthub.accountweb.controller;

import com.accounthub.accountweb.dto.CreateCustomerDto;
import com.accounthub.accountweb.dto.CustomerDto;
import com.accounthub.accountweb.dto.InfoAccountDto;
import com.accounthub.accountweb.exception.ErrorResponse;
import com.accounthub.accountweb.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    static final String BASE_URL = "/customers";

    CustomerService customerService;

    ObjectMapper objectMapper;

    public CustomerController(CustomerService customerService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
    }

    @ModelAttribute
    void getAllCustomers(Model model) throws JsonProcessingException {
        log.info("Request All Customers List ");
        try {
            ResponseEntity<List<CustomerDto>> response = customerService.getAllCustomers();
            log.info("Response  status => {} ",response.getStatusCode());
            model.addAttribute("customers", response.getBody());
        } catch (WebClientResponseException e) {
            log.error("Response creation new Customer Errors => {}", e.getStatusCode());
            log.error("Response creation new Customer Errors => {}", e.getResponseBodyAsString());
            model.addAttribute("errorMsg", objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class));
        }
    }

    @GetMapping
    String showCustomers(@ModelAttribute CreateCustomerDto createCustomerDto){
        return "customers";
    }

    @PostMapping
    String createNewCustomer(@Valid CreateCustomerDto customerDto, Errors errors, RedirectAttributes attributes) throws JsonProcessingException {
        log.info("Request creation new customer => {}", customerDto);

        if(errors.hasErrors()){
            log.error("Request creation new Customer Errors => {}", errors.getAllErrors());
            return "customers";
        }

        try {
            ResponseEntity response = customerService.newCustomer(customerDto);
            log.info("Response status => {}", response.getStatusCode());
        } catch (WebClientResponseException e) {
            log.error("Response creation new Customer Errors => {}", e.getStatusCode());
            log.error("Response creation new Customer Errors => {}", e.getResponseBodyAsString());

            attributes.addFlashAttribute("errorMsg", objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class));

            return "redirect:/customers";
        }

        attributes.addFlashAttribute("toastMessage", "Customers Successfully Created!");

        return "redirect:/customers";
    }
}
