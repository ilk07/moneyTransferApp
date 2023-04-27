package cp.moneytransferapp.exception;

public class OutOfService extends RuntimeException {
    public OutOfService(String msg) {
        super(msg);
    }
}
