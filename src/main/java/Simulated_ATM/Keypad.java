package Simulated_ATM;

import java.io.InputStream;
import java.util.Scanner;

public class Keypad {
    private Scanner input;

    //used in tests...
    public Keypad(InputStream input) {
        this.input = new Scanner(input);
    }

    public Keypad() {
        this.input = new Scanner(System.in);
    }

    public int getInput() {
        while( true){
            String userInput = input.nextLine();
            if( isValidInput(userInput))
                return Integer.parseInt(userInput);
            else
                System.out.println("Only numbers  are allowed.");
        }
    }

    private boolean isValidInput( String input)
    {
       for( int c = 0; c< input.length(); c++)
       {
           if( ! Character.isDigit( input.charAt(c)))
               return  false;
       }
       return  true;
    }
}
