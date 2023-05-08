package cp.moneytransferapp.dto.cardtocard.impl;

import cp.moneytransferapp.config.AppProperties;
import cp.moneytransferapp.dto.cardtocard.CardToCardMoneyTransferDTO;
import cp.moneytransferapp.dto.cardtocard.CardToCardMoneyTransferDTOBuilder;
import cp.moneytransferapp.dto.cardtocard.TransferConfirmationDTO;
import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferOperationId;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class DefaultCardToCardMoneyTransferBuilder implements CardToCardMoneyTransferDTOBuilder {
    @Autowired
    private AppProperties appProperties;

    public int calculateFee(int value) {
        return (int) ((appProperties.getTaxFee() / 100) * value);
    }

    @Override
    public CardToCardMoneyTransfer cardToCardMoneyTransferFromDTO(CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO) {
        return new CardToCardMoneyTransfer(
                cardToCardMoneyTransferDTO.getCardFromNumber(),
                cardToCardMoneyTransferDTO.getCardToNumber(),
                cardToCardMoneyTransferDTO.getCardFromCVV(),
                cardToCardMoneyTransferDTO.getCardFromValidTill(),
                cardToCardMoneyTransferDTO.getAmount(),
                calculateFee(cardToCardMoneyTransferDTO.getAmount().getValue()));
    }

    @Override
    public TransferOperationId transferOperationIdFromCardToCardMoneyTransfer(CardToCardMoneyTransfer cardToCardMoneyTransfer) {
        return new TransferOperationId(cardToCardMoneyTransfer.getTransferId());
    }

    @Override
    public TransferConfirmation transferConfirmationFromDTO(TransferConfirmationDTO transferConfirmationDTO) {
        return new TransferConfirmation(transferConfirmationDTO.getOperationId(), transferConfirmationDTO.getCode());
    }

}