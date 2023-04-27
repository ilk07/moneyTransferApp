package cp.moneytransferapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.NumberFormat;

@Getter
@AllArgsConstructor
public class CardToCardMoneyTransfer extends Transfer {
    private final String cardFromNumber;
    private final String cardToNumber;
    private final String cardFromCVV;
    private final String cardFromValidTill;
    private final Amount amount;
    private TransferStatus status;
    private String verificationCode;
    private final int fee;

    public CardToCardMoneyTransfer(String cardFromNumber, String cardToNumber, String cardFromCVV, String cardFromValidTill, Amount amount, int fee) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
        this.fee = fee;
        this.status = TransferStatus.AWAITING_CONFIRMATION; //default_status
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(amount.getCurrency().getDefaultFractionDigits());

        return "Card to card-> " + getTransferId() +
                " from card '" + cardFromNumber +
                ", to card  '" + cardToNumber +
                ", amount: " + format.format((double) amount.getValue() / 100).replace('\u00A0', ' ') +
                ", service tax: " + format.format((double) fee / 100).replace('\u00A0', ' ') +
                ", currency: " + amount.getCurrency().getCurrencyCode() +
                ", status: " + status;
    }
}
