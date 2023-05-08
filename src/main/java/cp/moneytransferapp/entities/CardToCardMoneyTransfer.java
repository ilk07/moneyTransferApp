package cp.moneytransferapp.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.NumberFormat;

@Setter
@Getter
@RequiredArgsConstructor
public class CardToCardMoneyTransfer extends Transfer {
    private final String cardFromNumber;
    private final String cardToNumber;
    private final String cardFromCVV;
    private final String cardFromValidTill;
    private final Amount amount;
    private TransferStatus status;
    private String verificationCode;
    private final int fee;

    @Override
    public String toString() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(amount.getCurrency().getDefaultFractionDigits());

        return "Card to card-> " + getTransferId() +
                " from card " + cardFromNumber +
                ", to card  " + cardToNumber +
                ", amount: " + format.format((double) amount.getValue() / 100).replace('\u00A0', ' ') +
                ", service tax: " + format.format((double) fee / 100).replace('\u00A0', ' ') +
                ", currency: " + amount.getCurrency().getCurrencyCode() +
                ", status: " + status;
    }
}
