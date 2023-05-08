package cp.moneytransferapp.service.impl;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferStatus;
import cp.moneytransferapp.logger.impl.TransferLogger;
import cp.moneytransferapp.repository.impl.TransferRepositoryInMemory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTransferServiceTest {
    private final UUID testId = UUID.randomUUID();

    @Mock
    TransferRepositoryInMemory repository;

    @Mock
    TransferLogger transferJournal;

    @InjectMocks
    private DefaultTransferService service;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start DefaultTransferServiceImplTest Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---DefaultTransferService Class Test Completed---");
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
                () -> {
                    service.confirmTransferOperation(transferConfirmationMock);
                }
        );

    }
}