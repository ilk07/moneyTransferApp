package cp.moneytransferapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TransferOperationId {
    private final String operationId;
}
