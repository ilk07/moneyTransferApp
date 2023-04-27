package cp.moneytransferapp.entities;

import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TransferConfirmationTest {

    TransferConfirmation sut;
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start TransferConfirmation Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---TransferConfirmation Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new TransferConfirmation("1234", "9999");
    }


    @Test
    @DisplayName("TransferConfirmation is Instance of TransferConfirmation Object")
    public void sutIsInstanceOfTransferConfirmation() {
        assertInstanceOf(TransferConfirmation.class, sut);
    }

    @Test
    @DisplayName("Amount object properties")
    public void transferConfirmation() {
        assertThat(sut, allOf(
                hasProperty(("operationId"), equalTo("1234")),
                hasProperty(("code"), equalTo("9999"))
        ));
    }

    @Test
    void getOperationId() {
        String expected = "1234";
        String actual = sut.getOperationId();

        assertEquals(expected, actual);
    }

    @Test
    void getCode() {
        String expected = "9999";
        String actual = sut.getCode();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Give Valid operation Id to construct object")
    void giveValidOperationIdToTransferConfirmationConstructor_shouldReturnEmptyErrorList() {

        String operationId = UUID.randomUUID().toString();

        TransferConfirmation transferConfirmation = new TransferConfirmation(operationId, sut.getCode());
        Set<ConstraintViolation<TransferConfirmation>> validates = validator.validate(transferConfirmation);

        assertTrue(validates.isEmpty());
    }

    @Test
    @DisplayName("Wrong operation Id to construct object")
    void givenOperationIdNotValid_shouldReturnErrorMessage() {

        TransferConfirmation transferConfirmation = new TransferConfirmation("1234", "code");
        Set<ConstraintViolation<TransferConfirmation>> validates = validator.validate(transferConfirmation);

        boolean actual = validates.stream().anyMatch(v -> v.getMessage().equals("Wrong operation Id"));

        assertTrue(validates.size() > 0);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Wrong operation Id to construct object")
    void givenCodeIsNotValid_shouldReturnErrorMessage() {

        TransferConfirmation transferConfirmation = new TransferConfirmation(UUID.randomUUID().toString(), "00");
        Set<ConstraintViolation<TransferConfirmation>> validates = validator.validate(transferConfirmation);

        boolean actual = validates.stream().anyMatch(v -> v.getMessage().equals("Wrong verification code"));

        assertTrue(validates.size() > 0);
        assertTrue(actual);
    }

}