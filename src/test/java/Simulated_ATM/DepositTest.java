package Simulated_ATM;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {

    private final List<Account> accounts = List.of( new Account(123456789,1234,
            200,200) );
    private final Database bankDataBase = new Database(accounts);
    private Screen screen = new Screen();
    private DepositSlot depositSlot = new DepositSlot();
    Keypad keypad;

    private ByteArrayOutputStream mockOutputStream;
    private InputStream mockInput;

    @BeforeEach
    public  void setUp()
    {
        mockOutputStream = new ByteArrayOutputStream();
        System.setOut( new PrintStream( mockOutputStream));
    }

    @AfterEach
    public  void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public  void shouldPromptForDepositAmountAndReturnAmount()
    {
        mockInput = new ByteArrayInputStream("100\n".getBytes());
        keypad = new Keypad(mockInput);
        Deposit deposit = new Deposit(123456789,screen,bankDataBase,keypad,depositSlot);

        assertEquals(100.0d, deposit.promptForDeposition());
    }

    @Test
    public void shouldUpdateAccountAfterDeposit()
    {
        mockInput = new ByteArrayInputStream("100\n".getBytes());
        keypad = new Keypad(mockInput);
        Deposit deposit = new Deposit(123456789, screen,bankDataBase, keypad, depositSlot);

        assertEquals(200, deposit.getBankDataBase().getAvailableBalance( deposit.getAccountNumber() ));
        assertEquals(200, deposit.getBankDataBase().getTotalBalance( deposit.getAccountNumber() ));

        deposit.execute();

        assertEquals(200, deposit.getBankDataBase().getAvailableBalance( deposit.getAccountNumber() ));
        assertEquals(300, deposit.getBankDataBase().getTotalBalance( deposit.getAccountNumber() ));

    }
}
