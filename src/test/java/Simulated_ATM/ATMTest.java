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


public class ATMTest {

    private List< Account> accounts =  List.of(new Account(123456789, 1234,
            100, 100));
    private  Screen screen = new Screen();
    private  Keypad atmKeypad;
    private  CashDispenser cashDispenser = new CashDispenser();
    private DepositSlot depositSlot = new DepositSlot();
    private Database database;

    private InputStream inputStream;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public  void setUp()
    {
        database= new Database( accounts);
        outputStream = new ByteArrayOutputStream();
        System.setOut( new PrintStream( outputStream));
    }
    @AfterEach
    public void tearDown()
    {
        System.setOut( System.out);
        outputStream = null;
    }

    @Test
    public void shouldAuthenticateUser()
    {
        inputStream = new ByteArrayInputStream("123456789\n1233".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.authenticateUser();
        assertFalse(atm.getUserAuthenticated());

        inputStream = new ByteArrayInputStream("123456789\n1234".getBytes());
        atm = new ATM( inputStream, database);
        atm.authenticateUser();
        assertTrue(atm.getUserAuthenticated());
    }

    @Test
    public void shouldDisplayMenuAndReturnUserChoice()
    {
        inputStream = new ByteArrayInputStream("1\n".getBytes());
        ATM atm = new ATM( inputStream, database);
         int userChoices = atm.displayMainMenu();
        String expectedOutput = """
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: """;
        assertEquals(expectedOutput, outputStream.toString().trim());
        assertEquals(1, userChoices);

    }

    @Test
    public void shouldCreateTransaction()
    {
        ATM atm = new ATM();
        Transaction currentTransaction = atm.createTransaction(1);
        assertTrue( currentTransaction instanceof BalanceInquiry);

        currentTransaction = atm.createTransaction(2);
        assertTrue( currentTransaction instanceof Withdrawal);

        currentTransaction = atm.createTransaction(3);
        assertTrue( currentTransaction instanceof Deposit);

        currentTransaction = atm.createTransaction(4);
        assertNull( currentTransaction);

    }


    /**
     * Integrated testing
     *
     */
    @Test
    public void runWithUserChooseExitCorrectInputs()
    {
        inputStream = new ByteArrayInputStream("123456789\n1234\n4\n".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
Welcome!
Please enter your account number: Enter your PIN:
 
---Main menu---
1 - View my balance
2 - Withdraw cash
3 - Deposit funds
4  - Exit

Enter a choice: 

Exiting the system...
Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));
    }

    @Test
    public void runWithUserChooseBalanceEnquiryCorrectInputs()
    {
        inputStream = new ByteArrayInputStream("123456789\n1234\n1\n".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN:
                 
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                ---Balance information---
                - Available balance: R100,00
                - Total balance: R100,00
                =============================
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));
    }

    @Test
    public void runWithUserWrongChoiceCorrectInputs()
    {

        inputStream = new ByteArrayInputStream("123456789\n1234\n7\n1".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN:
                 
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                You did not enter a valid selection. Try again.
                
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                ---Balance information---
                - Available balance: R100,00
                - Total balance: R100,00
                =============================
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));
    }

    @Test
    public void runWithUserChooseBalanceEnquiryInCorrectPinOrAccount() {
        inputStream = new ByteArrayInputStream("123456789\n1233\n123456798\n1234\n123456789\n1234\n1\n".getBytes());
        ATM atm = new ATM(inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN: 
                Invalid account number or PIN. Please try again.
                
                Please enter your account number: Enter your PIN: 
                Invalid account number or PIN. Please try again.
                
                Please enter your account number: Enter your PIN: 
                
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                                
                Enter a choice: 
                ---Balance information---
                - Available balance: R100,00
                - Total balance: R100,00
                =============================
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));
    }

    @Test
    public void runWithUserChooseWithdrawalCorrectInputs()
    {
        inputStream = new ByteArrayInputStream("123456789\n1234\n2\n4".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN:
                 
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                ---Withdrawal Menu---
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal
                
                Choose a withdrawal amount: 
                
                Your cash has been dispensed. Please take your cash now.
                
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));    }

    @Test
    public void runWithUserChooseWithdrawalInsufficientCashInAccount()
    {
        inputStream = new ByteArrayInputStream("123456789\n1234\n2\n5\n4\n".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN:
                 
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                ---Withdrawal Menu---
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal
                
                Choose a withdrawal amount: 
                
                Insufficient funds in your account.
                
                Please choose a small amount.
                
                ---Withdrawal Menu---
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal
                
                Choose a withdrawal amount: 
                
                Your cash has been dispensed. Please take your cash now.
                
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));    }

    @Test
    public void runWithUserChooseWithdrawalInsufficientCashInATM()
    {
        List< Account> accounts =  List.of(new Account(123456789, 1234,
                2000000, 2000000));
        inputStream = new ByteArrayInputStream("123456789\n1234\n2\n6\n4\n".getBytes());

        database = new Database( accounts);

        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN:
                 
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                ---Withdrawal Menu---
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal
                
                Choose a withdrawal amount: 
                
                Insufficient cash available in ATM.
                
                Please choose a small amount.
                
                ---Withdrawal Menu---
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal
                
                Choose a withdrawal amount: 
                
                Your cash has been dispensed. Please take your cash now.
                
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));    }

    @Test
    public void runWithUserChooseDepositeCorrectInputs()
    {
        inputStream = new ByteArrayInputStream("123456789\n1234\n3\n100".getBytes());
        ATM atm = new ATM( inputStream, database);
        atm.performTransactions();
        String expectedOutput = """
                Welcome!
                Please enter your account number: Enter your PIN:
                 
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: 
                Enter the amount to deposit (or 0 to cancel): 
                
                Please insert R100 on the depositSlot.
                
                Your money has been received.
                
                Thank you! Goodbye...!""";
        assertEquals(StringUtils.replace(expectedOutput,"\r",""),
                StringUtils.replace(outputStream.toString().trim(),"\r",""));    }
}
