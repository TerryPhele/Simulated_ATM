package Simulated_ATM;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseTest {

    private Database database;
    private List< Account> accountList = new ArrayList<>();

    @BeforeEach
    public  void setUp()
    {

        accountList.add(new Account(223456789, 2234,
                100,100));
        accountList.add( new Account(123456789, 1234,
                200,300));

        database = new Database(accountList);
    }

    @AfterEach
    public  void tearDown()
    {
        database = null;
        accountList.clear();
    }

    @Test
    public void shouldRetrieveAccountByAccountNumber()
    {
        Account currentAccount = database.getAccount(123456789);

        assertEquals( 200, currentAccount.getAvailableBalance());
        assertEquals( 300, currentAccount.getTotalBalance());
        assertTrue(currentAccount.validatePin(1234));
    }

    @Test
    public void shouldAuthenticateUserByAccountNumberAndPin()
    {
        assertTrue( database.authenticateUser(123456789, 1234));
        assertTrue( database.authenticateUser(223456789, 2234));
        assertFalse(database.authenticateUser(123456789,22345));
        assertFalse(database.authenticateUser(223456789,1234));
    }

    @Test
    public void shouldRetrieveAvailableBalanceOfAccountByAccountNumber()
    {
        assertEquals( 200,database.getAvailableBalance(123456789));
    }

    @Test
    public void shouldRetrieveTotalBalanceOfAccountByAccountNumber()
    {
        assertEquals( 300,database.getTotalBalance(123456789));
    }

    @Test
    public  void shouldUpdateAccountWhenCredited()
    {
        database.credit(123456789, 100);
        Account account = database.getAccount(123456789);
        assertEquals(400, account.getTotalBalance());
        assertEquals(200, account.getAvailableBalance());
    }

    @Test
    public  void shouldUpdateAccountWhenDebited()
    {
        database.debit(123456789, 100);
        Account account = database.getAccount(123456789);
        assertEquals(200, account.getTotalBalance());
        assertEquals(100, account.getAvailableBalance());
    }

}
