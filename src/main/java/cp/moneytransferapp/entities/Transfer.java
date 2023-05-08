package cp.moneytransferapp.entities;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@NoArgsConstructor
public abstract class Transfer {
    private final UUID transferId = UUID.randomUUID();
    public String getTransferId() {
        return transferId.toString();
    }
}
