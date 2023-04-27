package cp.moneytransferapp.logger;

import cp.moneytransferapp.logger.impl.TransferLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class TransferLoggerTest {

    TransferLogger sut = TransferLogger.getInstance();

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start TransferLogger Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---TransferLogger Class Test Completed---");
    }

    @Test
    @DisplayName("sut is Instance of TransferLogger")
    public void sutIsInstanceOfAmount() {
        assertInstanceOf(TransferLogger.class, sut);
    }

    @Test
    @DisplayName("sut is instance of TransferLogger")
    void getInstance_shouldReturnInstanceOfTransferLogger() {
        assertInstanceOf(TransferLogger.class, TransferLogger.getInstance());
    }

    @Test
    @DisplayName("Filename getter return given test name")
    void setAndGetFilename_shouldReturnGivenTestName() {

        String filename = "testTransferFileName.log";
        sut.setFilename(filename);

        String expected = filename;
        String actual = sut.getFilename();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Log test message with custom text content")
    void createLogMessage_shouldContainsTestMessageText() {
        try {
            Method method = TransferLogger.class.getDeclaredMethod("createLogMessage", String.class);
            method.setAccessible(true);

            String testMessage = "custom test message";
            String actual = (String) method.invoke(sut, testMessage);

            assertTrue(actual.contains(testMessage));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}