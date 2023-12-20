package Simulated_ATM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class BalanceEnquiryTest {

    private final List<Account>  accounts = List.of( new Account(123456789,1234,
            100,200) );
    private final Database bankDataBase = new Database(accounts);
    private Screen screen = new Screen();
    private final ByteArrayOutputStream mockOutputStream = new ByteArrayOutputStream();

    @BeforeEach
    public  void setUp()
    {
        System.setOut( new PrintStream( mockOutputStream));
    }

    @AfterEach
    public  void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public  void executeShouldDisplayAccountBalanceInformation()
    {
        BalanceInquiry balanceInquiry = new BalanceInquiry(123456789,screen,bankDataBase);
        balanceInquiry.execute();
        String expectedOutput = """
                ---Balance information---\r
                - Available balance: R100,00
                - Total balance: R200,00
                =============================""";
        assertEquals(expectedOutput, mockOutputStream.toString().trim());
    }


}