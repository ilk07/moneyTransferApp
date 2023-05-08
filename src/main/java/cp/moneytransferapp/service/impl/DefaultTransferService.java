package cp.moneytransferapp.service.impl;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferStatus;
import cp.moneytransferapp.logger.impl.TransferLogger;
import cp.moneytransferapp.repository.impl.TransferRepositoryInMemory;
import cp.moneytransferapp.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransferService implements TransferService {
    private final TransferRepositoryInMemory repository;

    @Autowired
    private final TransferLogger transferJournal;

    public DefaultTransferService(TransferRepositoryInMemory repository) {
        this.repository = repository;
        this.transferJournal = TransferLogger.getInstance();
    }

    public CardToCardMoneyTransfer transferRequest(CardToCardMoneyTransfer cardToCardMoneyTransfer) {
        cardToCardMoneyTransfer.setVerificationCode(verificationCode());
        cardToCardMoneyTransfer.setStatus(TransferStatus.AWAITING_CONFIRMATION);
        if (this.repository.saveTransfer(cardToCardMoneyTransfer)) {
            transferJournal.logToJournal(cardToCardMoneyTransfer.toString());
            return cardToCardMoneyTransfer;
        }
        throw new IllegalArgumentException("Unable to update verification code"); //код
    }

    public CardToCardMoneyTransfer confirmTransferOperation(TransferConfirmation transferConfirmation) {

        CardToCardMoneyTransfer cardToCardMoneyTransfer = this.repository.getCardToCardMoneyTransferByTransferId(transferConfirmation.getOperationId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown transfer id"));

        if (cardToCardMoneyTransfer.getStatus().equals(TransferStatus.AWAITING_CONFIRMATION)) {
            if (cardToCardMoneyTransfer.getVerificationCode().equals(transferConfirmation.getCode())) {
                cardToCardMoneyTransfer.setStatus(TransferStatus.CONFIRMED_TRANSFERRED);
                if (this.repository.saveTransfer(cardToCardMoneyTransfer)) {
                    transferJournal.logToJournal(cardToCardMoneyTransfer.toString());
                    return cardToCardMoneyTransfer;
                }
            } else {
                throw new IllegalArgumentException("Wrong verification code"); //код
            }
        }
        throw new IllegalArgumentException("Transfer declined, outdated");
    }


    @Override
    public String verificationCode() {
        return (String) "0000";
    }


}
