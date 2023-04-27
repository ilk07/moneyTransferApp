package cp.moneytransferapp.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.Currency;

@Getter
@ToString
public class Amount {
    private final Currency currency;
    private final int value;

    public Amount(String currency, int value) {
        isCurrencyCode(currency);
        this.currency = Currency.getInstance(currency);

        if (value > 0) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Transfer amount must be greater than 0");
        }
    }

    private boolean isCurrencyCode(String code) {
        try {
            Currency.getInstance(code);
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong currency code");
        }
        return true;
    }

}
