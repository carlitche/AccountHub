package com.accounthub.bankaccount.service;

import com.accounthub.bankaccount.dto.*;
import com.accounthub.bankaccount.entity.Account;
import com.accounthub.bankaccount.entity.Customer;
import com.accounthub.bankaccount.exception.NotFoundException;
import com.accounthub.bankaccount.repository.AccountRepository;
import com.accounthub.bankaccount.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

    AccountRepository accountRepository;

    CustomerRepository customerRepository;

    TransactionService transactionService;

    public AccountServiceImpl(AccountRepository accountRepository,CustomerRepository customerRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
    }

    @Override
    public InfoAccountDto findAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        Optional<List<TransactionDto>> transactions = transactionService.getAllTransactionFromAccountId(accountId);

        List<TransactionDto> transactionList = new ArrayList<>();
        transactions.ifPresent(transactionList::addAll);
        Customer customer = account.getCustomer();
        return new InfoAccountDto(customer.getName(),
                                    customer.getSurname(),
                                    account.getBalance(),
                                    transactionList);
    }

    @Override
    public Long createAccount(CreateAccountDto createAccountDto) {

        Customer customer = customerRepository.findById(createAccountDto.customerId())
                                                .orElseThrow(() -> new NotFoundException("Customer Not Found."));
        Account account = Account.builder()
                                    .balance(createAccountDto.initialCredit())
                                    .type("Current Account")
                                    .createdAt(LocalDateTime.now())
                                    .customer(customer)
                                    .build();

        Account newAccount = accountRepository.save(account);

        if(createAccountDto.initialCredit().compareTo(BigDecimal.ZERO) > 0){
            CreateTransactionDto newTransactionDto = new CreateTransactionDto(newAccount.getAccountId(), createAccountDto.initialCredit(), createAccountDto.initialCredit(), LocalDateTime.now(), "Initial Deposit.");
            transactionService.createTransaction(newTransactionDto);
        }

        return newAccount.getAccountId();
    }
}
