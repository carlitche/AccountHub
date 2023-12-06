package com.accounthub.bankaccount.endpoint;

import com.accounthub.bankaccount.dto.AccountDto;
import com.accounthub.bankaccount.dto.CreateAccountDto;
import com.accounthub.bankaccount.dto.CreateCustomerDto;
import com.accounthub.bankaccount.dto.CustomerDto;
import com.accounthub.bankaccount.service.CustomerService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomerService customerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create_new_customer() throws Exception {
        Long customerId = 1L;
        String json = """
                {
                    "name": "John",
                    "surname": "Doe",
                    "email": "john.doe@mail.com",
                    "phone" : "+3699999999"
                }
                """;

        when(customerService.createCustomer(objectMapper.readValue(json, CreateCustomerDto.class))).thenReturn(customerId);

        mvc.perform(post("/api/customers").content(json).contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/customers/" + customerId)));
    }

    @Test
    void get_customer_by_id() throws Exception {
        Long customerId = 1L;

        List<AccountDto> accounts = List.of(
                new AccountDto(1L, new BigDecimal("123.45"), "Personal", LocalDateTime.now(), customerId, null)
        );

        CustomerDto customerDto = new CustomerDto(customerId, "John", "Doe", "john.doe@mail.com", "+3699999999", accounts);

        when(customerService.getCustomer(customerId)).thenReturn(customerDto);

        mvc.perform(get("/api/customers/{customerId}", customerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(customerDto.customerId()));
    }

}
