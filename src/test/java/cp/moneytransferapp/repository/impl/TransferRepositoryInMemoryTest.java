package cp.moneytransferapp.repository.impl;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferRepositoryInMemoryTest {

    private final UUID testId = UUID.randomUUID();

    @InjectMocks
    private TransferRepositoryInMemory repository;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start TransferRepositoryInMemory Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---TransferRepositoryInMemory Test Completed---");
    }


    @Test
    @DisplayName("Repository can save data in memory")
    void saveTransfer_shouldReturnTrue() {
        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn(testId.toString());

        boolean actual = repository.saveTransfer(cardToCardMoneyTransferMock);

        assertTrue(actual);
    }

    @Test
    @DisplayName("Repository can check in memory list by wrong Id")
    void isTransferInListFalse_shouldReturnFalse() {

        boolean actual = repository.isTransferInList("code-id");

        assertFalse(actual);
    }

    @Test
    @DisplayName("Repository can check in memory list by correct Id")
    void isTransferInList_shouldReturnTrue() {

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn(testId.toString());
        repository.saveTransfer(cardToCardMoneyTransferMock);

        boolean actual = repository.isTransferInList(testId.toString());

        assertTrue(actual);
    }


    @Test
    @DisplayName("Repository can check in memory list by correct Id")
    void getCardToCardMoneyTransferByTransferId_shouldReturnOptionalCardToCardMoneyTransferObject_notNull_withTestId(){

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        when(cardToCardMoneyTransferMock.getTransferId()).thenReturn(testId.toString());
        repository.saveTransfer(cardToCardMoneyTransferMock);

        final var actual = repository.getCardToCardMoneyTransferByTransferId(testId.toString());

        assertInstanceOf(Optional.class, actual);
        assertNotNull(actual);
        assertEquals(testId.toString(), actual.get().getTransferId());
    }


    @Test
    @DisplayName("Repository can check in memory list by correct Id")
    void getCardToCardMoneyTransferByTransferId_shouldReturnOptionalEmpty(){

        final var actual = repository.getCardToCardMoneyTransferByTransferId(testId.toString());

        assertInstanceOf(Optional.class, actual);
        assertTrue(actual.isEmpty());

    }


}