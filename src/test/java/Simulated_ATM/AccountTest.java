package Simulated_ATM;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp()
    {
        this.account = new Account(1234567890, 1234,
                200,200);
    }

    @AfterEach
    public void tearDown()
    {
        this.account = null;
    }

    @Test
    public void shouldValidatePin()
    {
        assertFalse( account.validatePin(1231));
        assertTrue(account.validatePin(1234));
    }

    @Test
    public  void shouldCreditAmount()
    {
        account.credit(100);
        assertEquals(300, account.getTotalBalance());
        assertEquals(200,account.getAvailableBalance());
    }

    @Test
    public void shouldDebitAmount()
    {
        account.debit(100);
        assertEquals(100, account.getTotalBalance());
        assertEquals(100, account.getAvailableBalance());
    }

}
