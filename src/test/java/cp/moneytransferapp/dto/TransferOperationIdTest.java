package cp.moneytransferapp.dto;

import cp.moneytransferapp.entities.TransferOperationId;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TransferOperationIdTest {

    TransferOperationId sut;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start TransferOperationId Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---TransferOperationId Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new TransferOperationId("test-operation-id");
    }

    @Test
    @DisplayName("TransferOperationId is Instance of TransferOperationId Object")
    public void sutIsInstanceOfTransferOperationId() {
        assertInstanceOf(TransferOperationId.class, sut);
    }

    @Test
    @DisplayName("TransferOperationId properties")
    public void propertiesOfTransferOperationIdObject() {
        assertThat(sut, hasProperty("operationId", equalTo("test-operation-id")));
    }

    @Test
    @DisplayName("Get TransferOperationId of TransferOperationId Object")
    void getOperationId() {
        String expected = "test-operation-id";
        String actual = sut.getOperationId();

        assertEquals(expected, actual);
    }


}