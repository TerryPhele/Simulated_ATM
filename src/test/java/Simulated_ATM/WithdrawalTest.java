package Simulated_ATM;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class WithdrawalTest {
    private final List<Account> accounts = List.of( new Account(123456789,1234,
            200,200) );
    private final Database bankDataBase = new Database(accounts);
    private Screen screen = new Screen();
    private CashDispenser cashDispenser= new CashDispenser();
    Keypad keypad;


    private  ByteArrayOutputStream mockOutputStream;
    private  InputStream mockInput;



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
    public void shouldDisplayMenuOfAmmounts()
    {
        keypad = new Keypad();
        Withdrawal withdrawal = new Withdrawal(123456789, screen,bankDataBase, keypad, cashDispenser);
        withdrawal.displayMenuOfAmounts();

        String expectedOutput = """
                ---Withdrawal Menu---\r
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(mockOutputStream.toString().trim(),"\r",""));

    }

    @Test
    public  void shouldGetChosenAmountFromMenuOption()
    {
        mockInput = new ByteArrayInputStream("1\n".getBytes());
        keypad = new Keypad(mockInput);
        Withdrawal withdrawal = new Withdrawal(123456789, screen,bankDataBase, keypad, cashDispenser);
        assertEquals(20, withdrawal.getChosenAmountToWithDraw());
    }

    @Test
    public  void shouldUpdateAccountAfterWithdrawal()
    {
        mockInput = new ByteArrayInputStream("1\n".getBytes());
        keypad = new Keypad(mockInput);
        Withdrawal withdrawal = new Withdrawal(123456789, screen,bankDataBase, keypad, cashDispenser);

        assertEquals(200, withdrawal.getBankDataBase().getAvailableBalance( withdrawal.getAccountNumber() ));
        assertEquals(200, withdrawal.getBankDataBase().getTotalBalance( withdrawal.getAccountNumber() ));
        assertEquals(500, withdrawal.getCashDispenser().getNumberOfBillsNotesRemaining());


        withdrawal.execute(); //after withdrawal

        assertEquals(180, withdrawal.getBankDataBase().getAvailableBalance( withdrawal.getAccountNumber() ));
        assertEquals(180, withdrawal.getBankDataBase().getTotalBalance( withdrawal.getAccountNumber() ));
        assertEquals(499, withdrawal.getCashDispenser().getNumberOfBillsNotesRemaining());
    }

}
