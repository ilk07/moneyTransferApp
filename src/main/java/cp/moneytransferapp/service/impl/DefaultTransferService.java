package cp.moneytransferapp.service.impl;

import cp.moneytransferapp.exception.OutOfService;
import cp.moneytransferapp.logger.impl.TransferLogger;
import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferStatus;
import cp.moneytransferapp.repository.impl.TransferRepositoryInMemory;
import cp.moneytransferapp.service.TransferService;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransferService implements TransferService {
    final TransferRepositoryInMemory repository;
    protected TransferLogger transferJournal = TransferLogger.getInstance();


    public DefaultTransferService(TransferRepositoryInMemory repository) {
        this.repository = repository;
    }

    public CardToCardMoneyTransfer transferRequest(CardToCardMoneyTransfer cardToCardMoneyTransfer) {

        cardToCardMoneyTransfer.setVerificationCode(verificationCode());

        if (this.repository.saveTransfer(cardToCardMoneyTransfer)) {

            transferJournal.logToJournal(cardToCardMoneyTransfer.toString());
            return cardToCardMoneyTransfer;

        }

        throw new OutOfService("Service unavailable");
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
                throw new OutOfService("Service unavailable, please, try again later");

            } else {
                throw new IllegalArgumentException("Wrong verification code"); //код
            }
        }
        throw new IllegalArgumentException("Transfer declined, outdated");

    }

    public void setTransferJournal(TransferLogger transferJournal) {
        this.transferJournal = transferJournal;
    }

    @Override
    public String verificationCode() {
        return (String) "0000";
    }


}
