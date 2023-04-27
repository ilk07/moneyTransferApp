package cp.moneytransferapp.service.impl;

import cp.moneytransferapp.exception.OutOfService;
import cp.moneytransferapp.logger.impl.TransferLogger;
import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferStatus;
import cp.moneytransferapp.repository.impl.TransferRepositoryInMemory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTransferServiceTest {

    private final UUID testId = UUID.randomUUID();

    @Mock
    private TransferRepositoryInMemory repository;

    @InjectMocks
    private DefaultTransferService service;

    @Mock
    private TransferLogger transferLogger;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start DefaultTransferServiceImplTest Class Test---");

    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---DefaultTransferService Class Test Completed---");
    }

    @Test
    @DisplayName("Get operationId return CardToCardMoneyTransfer Object")
    void transferRequest_shouldReturnCardToCardTransferObject() {

        service.setTransferJournal(transferLogger);

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn(testId.toString());
        when(repository.saveTransfer(cardToCardMoneyTransferMock)).thenReturn(true);
        when(transferLogger.logToJournal(anyString())).thenReturn(true);

        String expected = testId.toString();
        final var actual = service.transferRequest(cardToCardMoneyTransferMock);

        assertInstanceOf(CardToCardMoneyTransfer.class, actual);
        assertEquals(expected, actual.getTransferId());

    }


    @Test
    @DisplayName("Get operationId Exception in case when repository can't save Transfer Object")
    void transferRequest_shouldThrowException() {

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(repository.saveTransfer(cardToCardMoneyTransferMock)).thenReturn(false);

        assertThrows(OutOfService.class, () -> service.transferRequest(cardToCardMoneyTransferMock));

    }

    @Test
    @DisplayName("Confirm Transfer Operation should return CardToCardMoneyTransfer Object")
    void confirmTransferOperation_shouldReturnCardToCardMoneyTransferObject() {
        service.setTransferJournal(transferLogger);
        final TransferConfirmation transferConfirmationMock = mock(TransferConfirmation.class);

        when(transferConfirmationMock.getOperationId()).thenReturn(testId.toString());
        when(transferConfirmationMock.getCode()).thenReturn("test-code");

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getStatus()).thenReturn(TransferStatus.AWAITING_CONFIRMATION);
        when(cardToCardMoneyTransferMock.getVerificationCode()).thenReturn("test-code");
        when(repository.getCardToCardMoneyTransferByTransferId(any(String.class))).thenReturn(Optional.of(cardToCardMoneyTransferMock));
        when(repository.saveTransfer(any(CardToCardMoneyTransfer.class))).thenReturn(true);
        when(transferLogger.logToJournal(anyString())).thenReturn(true);

        final var expected = cardToCardMoneyTransferMock;
        final var actual = service.confirmTransferOperation(transferConfirmationMock);

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Confirm Transfer Operation should throw Exception in case when repository can't save Transfer Object")
    void confirmTransferOperation_shouldThrowOutOfServiceException() {

        final TransferConfirmation transferConfirmationMock = mock(TransferConfirmation.class);
        when(transferConfirmationMock.getOperationId()).thenReturn("test");
        when(transferConfirmationMock.getCode()).thenReturn("test-code");
        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getStatus()).thenReturn(TransferStatus.AWAITING_CONFIRMATION);
        when(cardToCardMoneyTransferMock.getVerificationCode()).thenReturn("test-code");
        when(repository.getCardToCardMoneyTransferByTransferId(any(String.class))).thenReturn(Optional.of(cardToCardMoneyTransferMock));
        when(repository.saveTransfer(any(CardToCardMoneyTransfer.class))).thenReturn(false);

        assertThrows(OutOfService.class, () -> service.confirmTransferOperation(transferConfirmationMock));
    }

    @Test
    @DisplayName("Confirm Transfer Operation should throw IllegalArgumentException in case when verification code is wrong")
    void confirmTransferOperation_shouldThrowIllegalArgumentException_wrongVerificationCode() {

        final TransferConfirmation transferConfirmationMock = mock(TransferConfirmation.class);
        when(transferConfirmationMock.getOperationId()).thenReturn("test");
        when(transferConfirmationMock.getCode()).thenReturn("code");

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getStatus()).thenReturn(TransferStatus.AWAITING_CONFIRMATION);
        when(cardToCardMoneyTransferMock.getVerificationCode()).thenReturn("wrong");
        when(repository.getCardToCardMoneyTransferByTransferId(any(String.class))).thenReturn(Optional.of(cardToCardMoneyTransferMock));

        assertThrows(IllegalArgumentException.class,
               ()->{service.confirmTransferOperation(transferConfirmationMock);}
        );

    }

    @Test
    @DisplayName("Confirm Transfer Operation should throw IllegalArgumentException in case when transfer status is wrong")
    void confirmTransferOperation_shouldThrowIllegalArgumentException_wrongStatus() {
        final TransferConfirmation transferConfirmationMock = mock(TransferConfirmation.class);
        when(transferConfirmationMock.getOperationId()).thenReturn("test");
        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getStatus()).thenReturn(TransferStatus.CONFIRMED_TRANSFERRED);
        when(repository.getCardToCardMoneyTransferByTransferId(any(String.class))).thenReturn(Optional.of(cardToCardMoneyTransferMock));

        assertThrows(IllegalArgumentException.class,
                ()->{service.confirmTransferOperation(transferConfirmationMock);}
        );

    }

}