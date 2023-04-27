package cp.moneytransferapp.repository;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;

import java.util.Optional;

public interface TransferRepository {
    boolean saveTransfer(CardToCardMoneyTransfer cardToCardMoneyTransfer);

    boolean isTransferInList(String transferId);

    Optional<CardToCardMoneyTransfer> getCardToCardMoneyTransferByTransferId(String transferId);

}
