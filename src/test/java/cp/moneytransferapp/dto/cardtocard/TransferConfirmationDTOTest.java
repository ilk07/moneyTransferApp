package cp.moneytransferapp.dto.cardtocard;

import org.junit.jupiter.api.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TransferConfirmationDTOTest {

    TransferConfirmationDTO sut;
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start TransferConfirmationDTO Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---TransferConfirmationDTO Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new TransferConfirmationDTO("test_Id", "test_Code");
    }

    @Test
    @DisplayName("TransferConfirmationDTO is Instance of TransferConfirmationDTO")
    public void cardToCardMoneyTransferDTO() {
        assertInstanceOf(TransferConfirmationDTO.class, sut);
    }

    @Test
    @DisplayName("TransferConfirmationDTO properties")
    public void propertiesOfCardToCardMoneyTransferDTOObject() {
        assertThat(sut, allOf(
                hasProperty("operationId", equalTo("test_Id")),
                hasProperty("code", equalTo("test_Code"))
        ));
    }

    @Test
    @DisplayName("Get TransferConfirmationDTO Object operationId value")
    void getOperationId_shouldReturnTestOperationIdValue() {
        String expected = "test_Id";
        String actual = sut.getOperationId();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get TransferConfirmationDTO Object code value")
    void getCode_shouldReturnTestCodeValue() {
        String expected = "test_Code";
        String actual = sut.getCode();

        assertEquals(expected, actual);
    }

}