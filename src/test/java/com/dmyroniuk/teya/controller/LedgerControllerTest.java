package com.dmyroniuk.teya.controller;

import com.dmyroniuk.teya.app.TeyaTaskApplication;
import com.dmyroniuk.teya.controller.dto.BalanceDto;
import com.dmyroniuk.teya.controller.dto.HistoryDto;
import com.dmyroniuk.teya.controller.dto.OperationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static com.dmyroniuk.teya.OperationTestUtils.depositOperation;
import static com.dmyroniuk.teya.OperationTestUtils.withdrawOperation;
import static com.dmyroniuk.teya.controller.ApiConstants.BALANCE_API_CALL_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.DEPOSIT_API_CALL_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.HISTORY_API_CALL_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.LEDGER_API_URL;
import static com.dmyroniuk.teya.controller.ApiConstants.WITHDRAW_API_CALL_URL;
import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes=TeyaTaskApplication.class)
@AutoConfigureMockMvc
public class LedgerControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void smokeTestLedgerApi() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BalanceDto expectedBalance = new BalanceDto(new BigDecimal(2));
        mvc.perform(post(LEDGER_API_URL + DEPOSIT_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDto(new BigDecimal(2))))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedBalance)));
        expectedBalance = new BalanceDto(new BigDecimal(1));
        mvc.perform(post(LEDGER_API_URL + WITHDRAW_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDto(new BigDecimal(1))))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedBalance)));
        mvc.perform(get(LEDGER_API_URL + BALANCE_API_CALL_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBalance)));
        HistoryDto expectedHistory = new HistoryDto(asList(depositOperation(2), withdrawOperation(1)));
        mvc.perform(get(LEDGER_API_URL + HISTORY_API_CALL_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedHistory)));
    }

    @Test
    public void testNoRequestBodyForDepositCall() throws Exception {
        mvc.perform(post(LEDGER_API_URL + DEPOSIT_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidRequestBodyForDepositCall() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post(LEDGER_API_URL + DEPOSIT_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDto(null)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNoRequestBodyForWithdrawCall() throws Exception {
        mvc.perform(post(LEDGER_API_URL + WITHDRAW_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidRequestBodyForWithdrawCall() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post(LEDGER_API_URL + WITHDRAW_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDto(null)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCustomExceptionMapping() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post(LEDGER_API_URL + WITHDRAW_API_CALL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDto(new BigDecimal(1))))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
