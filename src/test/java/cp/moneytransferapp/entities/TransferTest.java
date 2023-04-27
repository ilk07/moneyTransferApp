package cp.moneytransferapp.entities;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TransferTest extends Transfer {

    TransferTest sut;

    @BeforeAll
    public static void startClassTest() {
        System.out.println("---Start Transfer Class Test---");
    }

    @AfterAll
    public static void endClassTest() {
        System.out.println("---Transfer Test Completed---");
    }

    @BeforeEach
    public void initOneTest() {
        sut = new TransferTest();
    }

    @Test
    @DisplayName("TransferTest compatible with Transfer class")
    public void transferTestCompatibleWithTransferClass() {
        assertThat(TransferTest.class, typeCompatibleWith(Transfer.class));
    }

    @Test
    @DisplayName("Transfer is Instance of Transfer Object")
    public void sutIsInstanceOfTransfer() {
        assertInstanceOf(Transfer.class, sut);
    }

    @Test
    @DisplayName("Transfer object properties")
    public void transferProperties() {
        assertThat(sut, hasProperty("transferId"));
    }

    @Test
    @DisplayName("Transfer object properties")
    public void getTransferIdReturnString() {
        assertInstanceOf(String.class, sut.getTransferId());
    }

    @Test
    @DisplayName("Transfer object properties transferId is UUID field type")
    public void transferIdIsUUIDField() {
        try {
            Field field = Transfer.class.getDeclaredField("transferId");
            field.setAccessible(true);
            assertInstanceOf(UUID.class, field.get(sut));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}