package cp.moneytransferapp.dto.cardtocard;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferOperationId;

public interface CardToCardMoneyTransferDTOBuilder {
    CardToCardMoneyTransfer cardToCardMoneyTransferFromDTO(CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO);
    TransferOperationId transferOperationIdFromCardToCardMoneyTransfer(CardToCardMoneyTransfer cardToCardMoneyTransfer);
    TransferConfirmation transferConfirmationFromDTO(TransferConfirmationDTO transferConfirmationDTO);
}

