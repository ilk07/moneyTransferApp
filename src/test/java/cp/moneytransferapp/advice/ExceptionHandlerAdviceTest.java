package cp.moneytransferapp.advice;

import cp.moneytransferapp.repository.impl.TransferRepositoryInMemory;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransferRepositoryInMemory repository;


    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start ExceptionHandlerAdvice Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---ExceptionHandlerAdvice Class Test Completed---");
    }

    @Test
    void outOfServiceHandler_shouldReturnException_EmptyData() throws Exception {
        when(repository.saveTransfer(any())).thenReturn(false);
        String requestBody = "{\"cardFromNumber\":\"4438231558582693\",\"cardToNumber\":\"4438231558582693\",\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"11/24\",\"amount\":{\"currency\":\"RUR\",\"value\":10000}}";
        mockMvc.perform(
                        post("/transfer")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(500))
                .andExpect(result -> assertEquals("Service unavailable", result.getResolvedException().getMessage()));

    }

    @Test
    @DisplayName("Unknown transfer id - IllegalArgumentException")
    public void illegalArgumentExceptionHandler_shouldReturnException_OperationIdIsUnknown() throws Exception {
        String requestBody = "{\"operationId\":\"147baa84-3cfa-4588-a86d-d632b5e4d8f6\",\"code\":\"0000\"}";
        mockMvc.perform(
                        post("/confirmOperation")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals("Unknown transfer id", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Wrong operation Id - MethodArgumentNotValidException")
    public void methodArgumentNotValidExceptionHandler_shouldReturnException_operationIdNotValid() throws Exception {
        String requestBody = "{\"operationId\":\"1478\",\"code\":\"0000\"}";
        mockMvc.perform(
                        post("/confirmOperation")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("Wrong operation Id")));
    }


}