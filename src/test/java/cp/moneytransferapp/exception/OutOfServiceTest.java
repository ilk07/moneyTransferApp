package cp.moneytransferapp.exception;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class OutOfServiceTest {

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start OutOfService Exception Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---OutOfService Exception Test Completed---");
    }

    @Test
    @DisplayName("Throw exactly OutOfService Exception")
    public void throwInvalidCredentials() {

        Executable executable = () -> {
            throw new OutOfService("OutOfService Exception");
        };

        assertThrowsExactly(OutOfService.class, executable);
    }

    @Test
    @DisplayName("OutOfService message test")
    public void outOfService_shouldReturnTestMessage() {

        String expected = "Test error message #1";

        OutOfService exceptionMessage = new OutOfService(expected);
        String actual = exceptionMessage.getMessage();

        assertEquals(expected, actual);
    }

}