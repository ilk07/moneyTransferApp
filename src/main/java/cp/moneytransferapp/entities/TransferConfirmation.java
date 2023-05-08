package cp.moneytransferapp.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
public class TransferConfirmation {
    @NotEmpty(message = "Empty operation id")
    @NotNull(message = "Operation id is null")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Wrong operation Id")
    private final String operationId;

    @NotEmpty(message = "Empty code")
    @NotNull(message = "Code is null")
    @Pattern(regexp = "^[0-9]{4}$", message = "Wrong verification code")
    private final String code;


}
