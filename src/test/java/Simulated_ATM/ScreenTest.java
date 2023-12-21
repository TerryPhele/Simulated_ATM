package Simulated_ATM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
public class ScreenTest {

    private final ByteArrayOutputStream mockOutputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut( new PrintStream( mockOutputStream));
    }

    @AfterEach
    public  void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void shouldDisplayMessageWithNewLine(){
        Screen screen = new Screen();
        screen.displayMessageLine("Welcome,");
        screen.displayMessageLine("To our atm.");
        String ExpectedOutput = "Welcome,\r\n" +
                "To our atm.";

        assertEquals(ExpectedOutput, mockOutputStream.toString().trim());
    }
    @Test
    public void shouldDisplayMessageWithNoNewline()
    {
        Screen screen = new Screen();
        screen.displayMessage("Welcome,");
        screen.displayMessage("To our atm.");
        String ExpectedOutput = "Welcome,To our atm.";

        assertEquals(ExpectedOutput, mockOutputStream.toString().trim());
    }

    @Test
    public void shouldDisplayRandAmountWith2DecimalPlaces()
    {
        Screen screen = new Screen();
        screen.displayAmount(20);
        String expected = "R20,00";
        assertEquals(expected, mockOutputStream.toString().trim());
    }
}
