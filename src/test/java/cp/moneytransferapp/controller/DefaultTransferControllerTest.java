package cp.moneytransferapp.controller;

import cp.moneytransferapp.dto.cardtocard.CardToCardMoneyTransferDTO;
import cp.moneytransferapp.dto.cardtocard.TransferConfirmationDTO;
import cp.moneytransferapp.dto.cardtocard.impl.DefaultCardToCardMoneyTransferBuilder;
import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferOperationId;
import cp.moneytransferapp.service.impl.DefaultTransferService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DefaultTransferControllerTest {
    @Mock
    private DefaultTransferService service;
    @Mock
    private DefaultCardToCardMoneyTransferBuilder transferDTOBuilder;
    @InjectMocks
    private DefaultTransferController controller;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start DefaultTransferController Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---DefaultTransferController Class Test Completed---");
    }

    @Test
    @DisplayName("Verify Controller cardToCardMoneyTransfer")
    void cardToCardMoneyTransfer_shouldCallServiceAndDTOBuilder() {

        final CardToCardMoneyTransfer cardToCardMoneyTransfer = mock(CardToCardMoneyTransfer.class);
        final CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO = mock(CardToCardMoneyTransferDTO.class);

        when(transferDTOBuilder
                .cardToCardMoneyTransferFromDTO(cardToCardMoneyTransferDTO))
                .thenReturn(cardToCardMoneyTransfer);

        controller.cardToCardMoneyTransfer(cardToCardMoneyTransferDTO);

        verify(transferDTOBuilder).cardToCardMoneyTransferFromDTO(cardToCardMoneyTransferDTO);
        verify(service).transferRequest(cardToCardMoneyTransfer);

    }


    @Test
    @DisplayName("Verify Controller confirmTransferOperation")
    void confirmTransferOperation_shouldCallService() {

        final CardToCardMoneyTransfer cardToCardMoneyTransferMock = mock(CardToCardMoneyTransfer.class);
        final TransferConfirmationDTO transferConfirmationDTOMock = mock(TransferConfirmationDTO.class);
        final TransferConfirmation transferConfirmationMock = mock(TransferConfirmation.class);
        final TransferOperationId transferOperationIdMock = mock(TransferOperationId.class);
        when(transferDTOBuilder.transferConfirmationFromDTO(transferConfirmationDTOMock)).thenReturn(transferConfirmationMock);
        when(service.confirmTransferOperation(transferConfirmationMock)).thenReturn(cardToCardMoneyTransferMock);
        when(transferDTOBuilder.transferOperationIdFromCardToCardMoneyTransfer(cardToCardMoneyTransferMock)).thenReturn(transferOperationIdMock);

        controller.confirmTransferOperation(transferConfirmationDTOMock);
        verify(service).confirmTransferOperation(transferConfirmationMock);
    }


}