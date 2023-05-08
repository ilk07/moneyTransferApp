package cp.moneytransferapp.dto.cardtocard.impl;

import cp.moneytransferapp.config.AppProperties;
import cp.moneytransferapp.dto.cardtocard.CardToCardMoneyTransferDTO;
import cp.moneytransferapp.dto.cardtocard.TransferConfirmationDTO;
import cp.moneytransferapp.entities.Amount;
import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferOperationId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DefaultCardToCardMoneyTransferBuilderTest {

    @InjectMocks
    private DefaultCardToCardMoneyTransferBuilder sut = new DefaultCardToCardMoneyTransferBuilder();

    @Mock
    private AppProperties appProperties;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start DefaultCardToCardMoneyTransferBuilder Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---DefaultCardToCardMoneyTransferBuilder Class Test Completed---");
    }

    @Test
    @DisplayName("DefaultCardToCardMoneyTransferBuilder is Instance of DefaultCardToCardMoneyTransferBuilder")
    public void sutIsInstanceOfDefaultCardToCardMoneyTransferBuilder() {
        assertInstanceOf(DefaultCardToCardMoneyTransferBuilder.class, sut);
    }

    @Test
    @DisplayName("Calculate fee")
    void calculateFee_shouldReturnIntFeeValue() {

        when(appProperties.getTaxFee()).thenReturn(10.0);
        sut.calculateFee(100);
        int expected = 10;

        assertEquals(expected, sut.calculateFee(100));

    }

    @Test
    void cardToCardMoneyTransferFromDTO_shouldReturnCardToCardMoneyTransferObject_withGivenFieldValues() {

        when(appProperties.getTaxFee()).thenReturn(1.0);

        final Amount amountMock = mock(Amount.class);
        final CardToCardMoneyTransferDTO cardToCardMoneyTransferDTOMock = mock(CardToCardMoneyTransferDTO.class);
        when(cardToCardMoneyTransferDTOMock.getCardFromNumber()).thenReturn("3764285348263861");
        when(cardToCardMoneyTransferDTOMock.getCardToNumber()).thenReturn("4438231558582693");
        when(cardToCardMoneyTransferDTOMock.getCardFromCVV()).thenReturn("123");
        when(cardToCardMoneyTransferDTOMock.getCardFromValidTill()).thenReturn("12/30");
        when(cardToCardMoneyTransferDTOMock.getAmount()).thenReturn(amountMock);
        when(cardToCardMoneyTransferDTOMock.getAmount().getValue()).thenReturn(100);

        final var actual = sut.cardToCardMoneyTransferFromDTO(cardToCardMoneyTransferDTOMock);

        assertInstanceOf(CardToCardMoneyTransfer.class, actual);
        assertThat(actual, allOf(
                hasProperty("cardFromNumber", equalTo("3764285348263861")),
                hasProperty("cardToNumber", equalTo("4438231558582693")),
                hasProperty("cardFromCVV", equalTo("123")),
                hasProperty("cardFromValidTill", equalTo("12/30")),
                hasProperty("fee"),
                hasProperty("status")
        ));
    }

    @Test
    void transferOperationIdFromCardToCardMoneyTransfer_shouldReturnTransferOperationIdObject_withGivenFieldValues() {

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn("test-id");

        final var actual = sut.transferOperationIdFromCardToCardMoneyTransfer(cardToCardMoneyTransferMock);

        assertInstanceOf(TransferOperationId.class, actual);
        assertThat(actual, hasProperty("operationId", equalTo("test-id")));
    }

    @Test
    void transferConfirmationFromDTO_shouldReturnTransferConfirmationObject_withGivenFieldValues() {

        final TransferConfirmationDTO transferConfirmationDTOMock = mock(TransferConfirmationDTO.class);

        when(transferConfirmationDTOMock.getOperationId()).thenReturn("test-id");
        when(transferConfirmationDTOMock.getCode()).thenReturn("test-code");

        final var actual = sut.transferConfirmationFromDTO(transferConfirmationDTOMock);

        assertInstanceOf(TransferConfirmation.class, actual);
        assertThat(actual, allOf(
                hasProperty("operationId", equalTo("test-id")),
                hasProperty("code", equalTo("test-code"))
        ));
    }

}