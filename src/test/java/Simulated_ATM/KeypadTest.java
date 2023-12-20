package Simulated_ATM;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class KeypadTest {
    private InputStream mockInput;


    @Test
    public void shouldGetInput(){
        mockInput = new ByteArrayInputStream("1\n".getBytes());
        Keypad keypad = new Keypad(mockInput);
        assertEquals(1, keypad.getInput());
    }
    @Test
    public void shouldGetCorrectInputForm(){
        mockInput = new ByteArrayInputStream("One\n1\n".getBytes());
        Keypad keypad = new Keypad(mockInput);
        assertEquals(1, keypad.getInput());
    }
}
