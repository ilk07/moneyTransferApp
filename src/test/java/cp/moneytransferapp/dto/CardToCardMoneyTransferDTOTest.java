package cp.moneytransferapp.dto;

import cp.moneytransferapp.dto.cardtocard.CardToCardMoneyTransferDTO;
import cp.moneytransferapp.entities.Amount;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

class CardToCardMoneyTransferDTOTest {

    CardToCardMoneyTransferDTO sut;
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start CardToCardMoneyTransferDTO Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---CardToCardMoneyTransferDTO Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        Amount amount = Mockito.mock(Amount.class);
        sut = new CardToCardMoneyTransferDTO();
        sut.setCardFromNumber("5552983744875475");
        sut.setCardToNumber("3764285348263861");
        sut.setCardFromCVV("123");
        sut.setCardFromValidTill("12/25");
        sut.setAmount(amount);
    }

    @Test
    @DisplayName("CardToCardMoneyTransferDTO is Instance of CardToCardMoneyTransferDTO Object")
    public void cardToCardMoneyTransferDTO() {
        assertInstanceOf(CardToCardMoneyTransferDTO.class, sut);
    }

    @Test
    @DisplayName("CardToCardMoneyTransferDTO properties")
    public void propertiesOfCardToCardMoneyTransferDTOObject() {
        assertThat(sut, allOf(
                hasProperty("cardFromNumber", equalTo("5552983744875475")),
                hasProperty("cardToNumber", equalTo("3764285348263861")),
                hasProperty("cardFromCVV", equalTo("123")),
                hasProperty("cardFromValidTill", equalTo("12/25"))
        ));
    }

    @Test
    @DisplayName("Get sender card number")
    void getCardFromNumber() {
        String expected = "5552983744875475";
        String actual = sut.getCardFromNumber();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get receiver card number")
    void getCardToNumber() {
        String expected = "3764285348263861";
        String actual = sut.getCardToNumber();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get sender card CVV code")
    void getCardFromCVV() {
        String expected = "123";
        String actual = sut.getCardFromCVV();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get sender card valid month/year")
    void getCardFromValidTill() {
        String expected = "12/25";
        String actual = sut.getCardFromValidTill();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Set sender card number")
    void setCardFromNumber() {

        String expected = "5569755825672968";
        sut.setCardFromNumber(expected);
        String actual = sut.getCardFromNumber();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Set receiver card number")
    void setCardToNumber() {
        String expected = "5569755825672968";
        sut.setCardToNumber(expected);
        String actual = sut.getCardToNumber();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Set sender card CVV code")
    void setCardFromCVV() {

        String expected = "999";
        sut.setCardFromCVV(expected);
        String actual = sut.getCardFromCVV();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get sender card valid month/year")
    void setCardFromValidTill() {

        String expected = "01/99";
        sut.setCardFromValidTill(expected);
        String actual = sut.getCardFromValidTill();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Wrong sender card number message from constructor")
    void givenCardFromNumberNotValid_shouldReturnErrorMessage() {

        CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO = new CardToCardMoneyTransferDTO(
                "123",
                sut.getCardToNumber(),
                sut.getCardFromCVV(),
                sut.getCardFromValidTill(),
                sut.getAmount()
        );

        sut.setCardFromNumber("123");

        Set<ConstraintViolation<CardToCardMoneyTransferDTO>> validates = validator.validate(cardToCardMoneyTransferDTO);

        boolean actual = validates.stream().anyMatch(v -> v.getMessage().equals("Wrong sender card number"));

        assertTrue(validates.size() > 0);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Wrong receiver card number message from constructor")
    void givenCardToNumberNotValid_shouldReturnErrorMessage() {
        CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO = new CardToCardMoneyTransferDTO(
                sut.getCardFromNumber(),
                "12345678912345678",
                sut.getCardFromCVV(),
                sut.getCardFromValidTill(),
                sut.getAmount()
        );
        Set<ConstraintViolation<CardToCardMoneyTransferDTO>> validates = validator.validate(cardToCardMoneyTransferDTO);

        boolean actual = validates.stream().anyMatch(v -> v.getMessage().equals("Wrong receiver card number"));

        assertTrue(validates.size() > 0);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Wrong sender card CVV code for constructor")
    void givenCardFromCVVNotValid_shouldReturnErrorMessage() {
        CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO = new CardToCardMoneyTransferDTO(
                sut.getCardFromNumber(),
                sut.getCardToNumber(),
                "1",
                sut.getCardFromValidTill(),
                sut.getAmount()
        );
        Set<ConstraintViolation<CardToCardMoneyTransferDTO>> validates = validator.validate(cardToCardMoneyTransferDTO);

        boolean actual = validates.stream().anyMatch(v -> v.getMessage().equals("Wrong CVV code"));

        assertTrue(validates.size() > 0);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Wrong sender card valid dates")
    void givenCardFromValidTillNotValid_shouldReturnErrorMessage() {
        CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO = new CardToCardMoneyTransferDTO(
                sut.getCardFromNumber(),
                sut.getCardToNumber(),
                sut.getCardFromCVV(),
                "1/32",
                sut.getAmount()
        );
        Set<ConstraintViolation<CardToCardMoneyTransferDTO>> validates = validator.validate(cardToCardMoneyTransferDTO);

        boolean actual = validates.stream().anyMatch(v -> v.getMessage().equals("Card valid date error"));

        assertTrue(validates.size() > 0);
        assertTrue(actual);
    }

}
