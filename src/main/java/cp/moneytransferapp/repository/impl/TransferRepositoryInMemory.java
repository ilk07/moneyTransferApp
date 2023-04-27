package cp.moneytransferapp.repository.impl;

import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.repository.TransferRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepositoryInMemory implements TransferRepository {
    private final Map<String, CardToCardMoneyTransfer> cardToCardTransferList = new ConcurrentHashMap<>();

    @Override
    public Optional<CardToCardMoneyTransfer> getCardToCardMoneyTransferByTransferId(String transferId) {
        return Optional.ofNullable(cardToCardTransferList.get(transferId));
    }

    @Override
    public boolean saveTransfer(CardToCardMoneyTransfer cardToCardMoneyTransfer) {
        cardToCardTransferList.put(cardToCardMoneyTransfer.getTransferId(), cardToCardMoneyTransfer);
        if(cardToCardTransferList.containsKey(cardToCardMoneyTransfer.getTransferId())){
            return true;
        }
        return false;
    }

    @Override
    public boolean isTransferInList(String transferId) {
        if (cardToCardTransferList.containsKey(transferId)) {
            return true;
        }
        return false;
    }
}
