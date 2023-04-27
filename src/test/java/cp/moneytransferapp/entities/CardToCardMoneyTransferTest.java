package cp.moneytransferapp.entities;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CardToCardMoneyTransferTest extends Transfer {

    CardToCardMoneyTransfer sut;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start Transfer Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---Transfer Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        Amount amount = Mockito.mock(Amount.class);

        sut = new CardToCardMoneyTransfer(
                "5552983744875475",
                "3764285348263861",
                "123",
                "12/25",
                amount,
                150
        );
    }

    @Test
    @DisplayName("sut is Instance of CardToCardMoneyTransfer")
    public void cardToCardMoneyTransfer_shouldBeInstanceOfCardToCardMoneyTransfer() {
        assertInstanceOf(CardToCardMoneyTransfer.class, sut);
    }

    @Test
    @DisplayName("CardToCardMoneyTransfer has properties")
    public void cardToCardMoneyTransferObject_shouldHaveSutProperties() {
        assertThat(sut, allOf(
                hasProperty("cardFromNumber", equalTo("5552983744875475")),
                hasProperty("cardToNumber", equalTo("3764285348263861")),
                hasProperty("cardFromCVV", equalTo("123")),
                hasProperty("cardFromValidTill", equalTo("12/25")),
                hasProperty("fee"),
                hasProperty("status", equalTo(TransferStatus.AWAITING_CONFIRMATION))
        ));
    }


    @Test
    @DisplayName("Set status")
    void setStatus() {
        sut.setStatus(TransferStatus.CONFIRMED);
        assertEquals(sut.getStatus(), TransferStatus.CONFIRMED);
    }

    @Test
    @DisplayName("Set VerificationCode")
    void setVerificationCode() {
        String expected = "1234";
        sut.setVerificationCode(expected);

        String actual = sut.getVerificationCode();

        assertEquals(expected, actual);
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
    @DisplayName("Get sender card valid date")
    void getCardFromValidTill() {
        String expected = "12/25";

        String actual = sut.getCardFromValidTill();

        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Get status")
    void getStatus() {
        final var expected = TransferStatus.AWAITING_CONFIRMATION;
        final var actual = sut.getStatus();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get verification code")
    void getVerificationCode() {
        String expected = "1234";
        sut.setVerificationCode(expected);

        final var actual = sut.getVerificationCode();

        assertEquals(expected, actual);
    }

    @Test
    void getFee() {
        int expected = 150;
        int actual = sut.getFee();
        assertEquals(expected, actual);
    }
}