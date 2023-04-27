package cp.moneytransferapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferAppApplicationTest {

    @Autowired
    TestRestTemplate restTemplate;
    private final static HttpHeaders headers = new HttpHeaders();
    private final static String URL = "http://localhost";

    private final static String CONTAINER_NAME = "money-transfer";

    private final static int LOCALHOST_PORT = 5500;
    public static GenericContainer<?> moneyTransferApp = new GenericContainer<>(CONTAINER_NAME).withExposedPorts(LOCALHOST_PORT);

    private final static String transferEndPoint = "/transfer";
    private final static String confirmOperationEndPoint = "/confirmOperation";


    @BeforeAll
    public static void setUp() {
        moneyTransferApp.start();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("Start " + CONTAINER_NAME + " container test");
    }

    @AfterAll
    public static void endTests() {
        System.out.println("End of " + CONTAINER_NAME + " container test");
    }

    @Test
    @DisplayName("Request with correct transfer data")
    void endPointTransfer_shouldReturnOperationIdOnly_andStatusOk() {

        String requestObjectJsonString = "{\"cardFromNumber\":\"4438231558582693\",\"cardToNumber\":\"4438231558582693\",\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"11/24\",\"amount\":{\"currency\":\"RUR\",\"value\":10000}}";

        HttpEntity<String> request = new HttpEntity<>(requestObjectJsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL + ":" + moneyTransferApp.getMappedPort(LOCALHOST_PORT) + transferEndPoint, request, String.class);

        JSONObject responseBodyObject = jsonFromString(response.getBody());

        String expected = "operationId";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(responseBodyObject.names().toString().contains(expected));
        assertEquals(1, responseBodyObject.names().length());
        assertTrue(responseBodyObject.opt(expected).toString().length() > 0);

    }

    @Test
    @DisplayName("Transfer error - invalid sender card number")
    void endPointTransfer_shouldReturnErrorInputDataViaCardFromNumber_andStatusBadRequest() {

        String requestObjectJsonString = "{\"cardFromNumber\":\"0000111122223333\",\"cardToNumber\":\"4438231558582693\",\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"11/24\",\"amount\":{\"currency\":\"RUR\",\"value\":10000}}";

        HttpEntity<String> request = new HttpEntity<>(requestObjectJsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL + ":" + moneyTransferApp.getMappedPort(LOCALHOST_PORT) + transferEndPoint, request, String.class);

        JSONObject responseBodyObject = jsonFromString(response.getBody());

        String expected = "Wrong sender card number";

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(responseBodyObject.names().toString().contains("id"));
        assertTrue(responseBodyObject.names().toString().contains("message"));
        assertTrue(responseBodyObject.opt("message").toString().contains(expected));
    }

    @Test
    @DisplayName("Transfer error - invalid amount value in request")
    void endPointTransfer_shouldReturnErrorInputDataViaAmountValue_andStatusInternalServerError() {

        String requestObjectJsonString = "{\"cardFromNumber\":\"4438231558582693\",\"cardToNumber\":\"4438231558582693\",\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"11/24\",\"amount\":{\"currency\":\"RUR\",\"value\":0}}";

        HttpEntity<String> request = new HttpEntity<>(requestObjectJsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL + ":" + moneyTransferApp.getMappedPort(LOCALHOST_PORT) + transferEndPoint, request, String.class);

        JSONObject responseBodyObject = jsonFromString(response.getBody());

        String expected = "Transfer amount must be greater than 0";

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(responseBodyObject.names().toString().contains("id"));
        assertTrue(responseBodyObject.names().toString().contains("message"));
        assertTrue(responseBodyObject.opt("message").toString().contains(expected));
    }


    @Test
    @DisplayName("Awaiting for success confirm operation with correct transfer data")
    void endPointConfirmOperation_shouldReturnOperationIdOnly_andStatusOk() {


        String requestObjectJsonString = "{\"cardFromNumber\":\"4438231558582693\",\"cardToNumber\":\"4438231558582693\",\"cardFromCVV\":\"123\",\"cardFromValidTill\":\"11/24\",\"amount\":{\"currency\":\"RUR\",\"value\":100}}";
        HttpEntity<String> transferRequest = new HttpEntity<>(requestObjectJsonString, headers);
        ResponseEntity<String> transferResponse = restTemplate.postForEntity(URL + ":" + moneyTransferApp.getMappedPort(LOCALHOST_PORT) + transferEndPoint, transferRequest, String.class);

        JSONObject transferOperationIdJson = jsonFromString(transferResponse.getBody());

        String expected = transferOperationIdJson.opt("operationId").toString();

        String requestConfirmationObjectJsonString = "{\"operationId\":\"" + expected + "\",\"code\":\"0000\"}";

        HttpEntity<String> request = new HttpEntity<>(requestConfirmationObjectJsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL + ":" + moneyTransferApp.getMappedPort(LOCALHOST_PORT) + confirmOperationEndPoint, request, String.class);

        JSONObject responseBodyObject = jsonFromString(response.getBody());
        String actual = responseBodyObject.opt("operationId").toString();

        assertEquals(expected, actual);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(responseBodyObject.names().toString().contains("operationId"));
        assertEquals(1, responseBodyObject.names().length());
        assertTrue(responseBodyObject.opt("operationId").toString().length() > 0);
    }

    @Test
    @DisplayName("Awaiting for exception with invalid transfer data")
    void endPointConfirmOperation_shouldReturnErrorInputDataViaOperationId_andStatusBadRequest() {

        String requestObjectJsonString = "{\"operationId\":\"1234\",\"code\":\"0000\"}";

        HttpEntity<String> request = new HttpEntity<>(requestObjectJsonString, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL + ":" + moneyTransferApp.getMappedPort(LOCALHOST_PORT) + confirmOperationEndPoint, request, String.class);
        JSONObject responseBodyObject = jsonFromString(response.getBody());

        String expected = "Wrong operation Id";

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(responseBodyObject.names().toString().contains("id"));
        assertTrue(responseBodyObject.names().toString().contains("message"));
        assertTrue(responseBodyObject.opt("message").toString().contains(expected));
    }

    private JSONObject jsonFromString(String data) {
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
