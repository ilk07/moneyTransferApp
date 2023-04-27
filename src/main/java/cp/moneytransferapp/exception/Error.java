package cp.moneytransferapp.exception;

import lombok.Getter;
import lombok.ToString;

import java.util.Random;

@Getter
@ToString
public class Error {

    private final int id;
    private final String message;

    public Error(String message) {
        this.id = generateId();
        this.message = message;
    }

    private int generateId() {
        Random random = new Random();
        return random.nextInt(300);
    }

}