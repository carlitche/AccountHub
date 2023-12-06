package com.accounthub.banktransaction.endpoint;

import com.accounthub.banktransaction.dto.CreateTransactionDto;
import com.accounthub.banktransaction.entity.Transaction;
import com.accounthub.banktransaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@ActiveProfiles("test")
public class TransactionControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    TransactionService transactionService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create_new_transaction() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        Long transactionId = 1l;
        String json = """
                {
                    "amount": 20.00,
                    "balance": 20.00,
                    "createdAt": "2023-11-21T18:00:00.00Z",
                    "description": "Initial Credit"
                }
                """;

        when(transactionService.createNewTransaction(objectMapper.readValue(json, CreateTransactionDto.class))).thenReturn(transactionId);

        mvc.perform(post("/api/transactions").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/transactions/" + transactionId)));
    }

    @Test
    void get_list_transaction_by_accountId() throws Exception {
        Long accountId = 1L;
        List<Transaction> transactionList = List.of(
                Transaction.builder()
                        .transactionId(1L)
                        .accountId(accountId)
                        .amount(new BigDecimal("20.45"))
                        .balance(new BigDecimal("20.45"))
                        .createdAt(LocalDateTime.now())
                        .description("Initial Deposit")
                        .build(),
                Transaction.builder()
                        .transactionId(2L)
                        .accountId(accountId)
                        .amount(new BigDecimal("10.00"))
                        .balance(new BigDecimal("10.45"))
                        .createdAt(LocalDateTime.now())
                        .description("Cash withdraw")
                        .build()
        );

        when(transactionService.getListTransactionByAccountId(accountId)).thenReturn(transactionList);

        mvc.perform(get("/api/transactions").param("accountId", String.valueOf(accountId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

    }
}
