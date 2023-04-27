package cp.moneytransferapp.dto.cardtocard;

import cp.moneytransferapp.entities.Amount;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Component
@Getter
@Setter
public class CardToCardMoneyTransferDTO {
    @NotEmpty(message = "Sender card number is empty")
    @CreditCardNumber(message = "Wrong sender card number")
    private String cardFromNumber;

    @NotEmpty(message = "Receiver card number is empty")
    @CreditCardNumber(message = "Wrong receiver card number")
    private String cardToNumber;

    @NotEmpty(message = "CVV code is empty")
    @Pattern(regexp = "^[0-9]{3}$", message = "Wrong CVV code")
    private String cardFromCVV;

    @NotEmpty(message = "Card valid date is empty")
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})", message = "Card valid date error")
    private String cardFromValidTill;


    @NotNull(message = "Amount is empty, Currency is empty")
    private Amount amount;

    public CardToCardMoneyTransferDTO() {
    }

    public CardToCardMoneyTransferDTO(String cardFromNumber, String cardToNumber, String cardFromCVV, String cardFromValidTill, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
    }
}
