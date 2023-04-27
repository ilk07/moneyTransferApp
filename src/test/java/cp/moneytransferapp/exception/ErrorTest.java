package cp.moneytransferapp.exception;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ErrorTest {

    Error sut;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start Error Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---Error Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new Error("Error class test message");
    }

    @Test
    @DisplayName("Error is Instance of Error Object")
    public void sutIsInstanceOfError() {
        assertInstanceOf(Error.class, sut);
    }

    @Test
    @DisplayName("Error properties")
    public void Error() {
        assertThat(sut, allOf(
                hasProperty("id", greaterThan(0)),
                hasProperty("message", equalTo("Error class test message"))
        ));
    }

    @Test
    @DisplayName("Get error Id")
    void getId() {
        int actual = sut.getId();
        assertThat(actual, greaterThan(0));
    }


    @Test
    @DisplayName("Get error message")
    void getMessage() {
        String actual = sut.getMessage();
        String expected = "Error class test message";

        assertThat(expected, equalTo(actual));
    }
}