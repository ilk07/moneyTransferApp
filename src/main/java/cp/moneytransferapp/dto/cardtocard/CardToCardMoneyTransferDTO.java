package cp.moneytransferapp.dto.cardtocard;

import cp.moneytransferapp.entities.Amount;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
public class CardToCardMoneyTransferDTO {
    @NotEmpty(message = "Sender card number is empty")
    @CreditCardNumber(message = "Wrong sender card number")
    private final String cardFromNumber;

    @NotEmpty(message = "Receiver card number is empty")
    @CreditCardNumber(message = "Wrong receiver card number")
    private final String cardToNumber;

    @NotEmpty(message = "CVV code is empty")
    @Pattern(regexp = "^[0-9]{3}$", message = "Wrong CVV code")
    private final String cardFromCVV;

    @NotEmpty(message = "Card valid date is empty")
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})", message = "Card valid date error")
    private final String cardFromValidTill;

    @NotNull(message = "Amount is empty, Currency is empty")
    private final Amount amount;

}
