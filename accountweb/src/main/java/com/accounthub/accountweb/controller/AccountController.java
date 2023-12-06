package com.accounthub.accountweb.controller;

import com.accounthub.accountweb.dto.CreateAccountDto;
import com.accounthub.accountweb.dto.InfoAccountDto;
import com.accounthub.accountweb.exception.ErrorResponse;
import com.accounthub.accountweb.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping(AccountController.BASE_URL)
public class AccountController {
    static final String BASE_URL = "/accounts";

    AccountService accountService;

    ObjectMapper objectMapper;

    public AccountController(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    String showForm(@ModelAttribute CreateAccountDto createAccountDto, @ModelAttribute InfoAccountDto infoAccountDto) {
        return "accounts";
    }

    @PostMapping
    String createNewAccount(@Valid CreateAccountDto accountDto, Errors errors, RedirectAttributes attributes) throws JsonProcessingException {
        log.info("Request creation new Account => {}", accountDto);

        if(errors.hasErrors()){
            log.error("Request creation new Account Errors => {}", errors.getAllErrors());
            return "accounts";
        }

        ResponseEntity response = null;
        try {
            response = accountService.newAccount(accountDto);
            log.info("Response status => {}", response.getStatusCode());

        } catch (WebClientResponseException e) {
            log.error("Response creation new Account Errors => {}", e.getStatusCode());
            log.error("Response creation new Account Errors => {}", e.getResponseBodyAsString());

            attributes.addFlashAttribute("errorMsg", objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class));

            return "redirect:/accounts";
        }

        attributes.addFlashAttribute("toastMessage", "Account Successfully Created!");

        return "redirect:/accounts";
    }

    @GetMapping("/")
    @Validated
    String getAccountDetail(@NotNull @Positive(message = "Customer ID is not valid!") @RequestParam  Long accountId,
                            RedirectAttributes redirectedAttributes) throws JsonProcessingException {
        log.info("Get Account Details for account ID => {}", accountId);

        try {
            ResponseEntity<InfoAccountDto> response = accountService.getAccountById(accountId);
            InfoAccountDto accountDetails = response.getBody();

            log.info("Response Account Details => {}", accountDetails);

            redirectedAttributes.addFlashAttribute("accountDetails", accountDetails);
        } catch (WebClientResponseException e) {
            log.error("Response creation new Account Errors => {}", e.getStatusCode());
            log.error("Response creation new Account Errors => {}", e.getResponseBodyAsString());
            redirectedAttributes.addFlashAttribute("errorMsg", objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class));
            return "redirect:/accounts";
        }


        return "redirect:/accounts";
    }

}
