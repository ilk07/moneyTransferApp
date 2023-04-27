package cp.moneytransferapp.controller;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.service.impl.DefaultTransferService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DefaultTransferControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultTransferService service;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start DefaultTransferController Class SpringBoot Mvc Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---DefaultTransferController Class SpringBoot Mvc Test Completed---");
    }

    @Test
    @DisplayName("EndPoint /transfer success request")
    void cardToCardMoneyTransfer_shouldReturnOkAndTransferOperationIdObjectData() throws Exception {

        CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn("7786d087-0e57-4cbb-95d4-27b251cba1c1");
        when(service.transferRequest(any())).thenReturn(cardToCardMoneyTransferMock);

        String requestBody = "{\"cardFromNumber\":\"4438231558582693\",\"cardToNumber\":\"4438231558582693\",\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"11/24\",\"amount\":{\"currency\":\"RUR\",\"value\":10000}}";
        mockMvc.perform(
                        post("/transfer")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("operationId")));
    }

    @Test
    @DisplayName("EndPoint /confirmOperation success request")
    void confirmTransferOperation_shouldReturnOkAndTransferOperationIdObjectData() throws Exception {

        CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn("7786d087-0e57-4cbb-95d4-27b251cba1c1");
        when(service.confirmTransferOperation(any())).thenReturn(cardToCardMoneyTransferMock);

        String requestBody = "{\"code\":\"0000\",\"operationId\":\"7786d087-0e57-4cbb-95d4-27b251cba1c1\"}";
        mockMvc.perform(
                        post("/confirmOperation")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("operationId")));
    }
}
