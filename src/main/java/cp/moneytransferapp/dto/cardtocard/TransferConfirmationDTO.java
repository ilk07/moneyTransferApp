package cp.moneytransferapp.dto.cardtocard;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class TransferConfirmationDTO {

    @NotEmpty(message = "Empty operation id")
    @NotNull(message = "Operation id is null")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Wrong operation Id")
    private String operationId;

    @NotEmpty(message = "Empty code")
    @NotNull(message = "Code is null")
    @Pattern(regexp = "^[0-9]{4}$", message = "Wrong verification code")
    private String code;

    public TransferConfirmationDTO(){

    }

}
