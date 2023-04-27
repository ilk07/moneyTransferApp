package cp.moneytransferapp.entities;

import lombok.Getter;
import lombok.ToString;
import java.util.UUID;

@Getter
@ToString
public abstract class Transfer {
    private final UUID transferId;
    public Transfer() {
        this.transferId = UUID.randomUUID();
    }
    public String getTransferId(){
        return transferId.toString();
    }
}
