package Simulated_ATM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CashDispenserTest {

    private  CashDispenser cashDispenser;

    @BeforeEach
    public  void setup()
    {
        cashDispenser = new CashDispenser();
    }
    @AfterEach
    public void tearDown()
    {
        this.cashDispenser=null;
    }

    @Test
    public void shouldSubtractBillsWhenDispensingCash() {

        assertEquals(500, cashDispenser.getNumberOfBillsNotesRemaining());
        cashDispenser.dispenseCash(40);
        assertEquals(498, cashDispenser.getNumberOfBillsNotesRemaining());
    }

    @Test
    public void shouldCheckIfCashIsAvailableForDispense()
    {
        assertTrue( cashDispenser.isSufficientCashAvailable(10000));
        assertFalse( cashDispenser.isSufficientCashAvailable(20000));

    }
}
