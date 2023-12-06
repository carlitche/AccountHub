package com.accounthub.bankaccount.endpoint;

import com.accounthub.bankaccount.dto.AccountDto;
import com.accounthub.bankaccount.dto.CreateAccountDto;
import com.accounthub.bankaccount.dto.InfoAccountDto;
import com.accounthub.bankaccount.dto.TransactionDto;
import com.accounthub.bankaccount.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
public class AccountControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountService accountService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void get_account_by_id_return200() throws Exception {
        Long accountId = 1L;
        List<TransactionDto> list = List.of(
                new TransactionDto(1L, new BigDecimal("123.45"), new BigDecimal("123.45"), LocalDateTime.now(), "Initial Deposit")
        );
//        AccountDto dto = new AccountDto(accountId, new BigDecimal("123.45"), "Personal", LocalDateTime.now(), 12345L, list);

        InfoAccountDto accountDto = new InfoAccountDto("John", "Doe", new BigDecimal("123.45"), list);

        when(accountService.findAccountById(accountId)).thenReturn(accountDto);

        mvc.perform(get("/api/accounts/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(accountDto.name()));
    }

    @Test
    void post_new_account_return201() throws Exception {

        String json = """
                {
                    "customerId": 1,
                    "initialCredit": 20.00
                }
                """;
        Long newAccountId = 1L;
        when(accountService.createAccount(objectMapper.readValue(json, CreateAccountDto.class))).thenReturn(newAccountId);

        mvc.perform(post("/api/accounts").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/accounts/" + newAccountId)));
    }

}
