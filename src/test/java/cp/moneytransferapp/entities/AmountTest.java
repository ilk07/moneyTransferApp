package cp.moneytransferapp.entities;

import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Currency;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

class AmountTest {

    Amount sut;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start Amount Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---Amount Class Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new Amount("RUR", 1000);
    }

    @Test
    @DisplayName("Error is Instance of Error Object")
    public void sutIsInstanceOfAmount() {
        assertInstanceOf(Amount.class, sut);
    }

    @Test
    @DisplayName("Amount object properties")
    public void Amount() {
        assertThat(sut, allOf(
                hasProperty("currency"),
                hasProperty("value")
        ));
    }


    @Test
    @DisplayName("Get Currency of Amount object is Currency Instance")
    void getCurrency() {
        assertInstanceOf(Currency.class, sut.getCurrency());
    }

    @Test
    @DisplayName("Get Currency of Amount object is RUR")
    void getCurrencyCurrencyValue() {

        Currency expected = Currency.getInstance("RUR");
        Currency actual = sut.getCurrency();

        assertThat(expected, equalTo(actual));
    }

    @Test
    @DisplayName("Get value of Amount object")
    void getValue() {
        int expected = 1000;
        int actual = sut.getValue();
        assertThat(expected, equalTo(actual));
    }

    @Test
    @DisplayName("Construct  value of Amount object")
    void newAmountConstructor_shouldThrowException_forZeroValue() {

        assertThrows(IllegalArgumentException.class, () -> new Amount("RUR", 0));

    }

    @Test
    @DisplayName("Currency code validation method - valid argument")
    void isCurrencyCodeTrue_shouldReturnTrue_validCurrencyCode() {

        try {
            Method method = Amount.class.getDeclaredMethod("isCurrencyCode", String.class);
            method.setAccessible(true);

            String code = "RUR";
            boolean expected = true;

            assertEquals(expected, method.invoke(sut, code));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("IllegalArgumentException in Currency Code validation method")
    public void isCurrencyCode_shouldThrowIllegalArgumentExceptionException_notValidCurrencyCode() {
        try {
            Method method = Amount.class.getDeclaredMethod("isCurrencyCode", String.class);
            method.setAccessible(true);

            String code = "srt";

            Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(sut, code));
            assertEquals(IllegalArgumentException.class, exception.getCause().getClass());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}