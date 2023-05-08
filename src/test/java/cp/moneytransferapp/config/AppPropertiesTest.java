package cp.moneytransferapp.config;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AppPropertiesTest {

    AppProperties sut;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start AppProperties Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---AppProperties Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new AppProperties();
        sut.setLogFilename("test.filename");
        sut.setTaxFee(2.5);
    }

    @Test
    @DisplayName("AppProperties sut is Instance of AppProperties")
    public void AppProperties() {
        assertInstanceOf(AppProperties.class, sut);
    }

    @Test
    void getLogFilename() {
        String expected = "test.filename";
        String actual = sut.getLogFilename();

        assertEquals(expected, actual);
    }

    @Test
    void getTaxFee() {
        double expected = 2.5;
        final var actual = sut.getTaxFee();

        assertEquals(expected, actual);

    }

    @Test
    void setLogFilename() {

        String expected = "set-test.filename";
        sut.setLogFilename(expected);
        String actual = sut.getLogFilename();

        assertEquals(expected, actual);
    }

    @Test
    void setTaxFee() {
        double expected = 3.1;
        sut.setTaxFee(expected);
        final var actual = sut.getTaxFee();

        assertEquals(expected, actual);
    }
}