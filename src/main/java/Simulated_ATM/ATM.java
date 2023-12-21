package Simulated_ATM;

import java.io.InputStream;
import java.util.Scanner;

public class ATM {

    private boolean userAuthenticated;
    private int currentAccountNUmber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private Database database;

    private static final int BALANCE_ENQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;

    public ATM() {
        userAuthenticated = false;
        currentAccountNUmber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        database = new Database();
    }

    //For Testing
    public ATM(InputStream input, Database bankDatabase) {
        userAuthenticated = false;
        currentAccountNUmber = 0;
        screen = new Screen();
        keypad = new Keypad( input);
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        database = bankDatabase;
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }


    public boolean getUserAuthenticated()
    {
        return  this.userAuthenticated;
    }

    public  void run(){
        while (true)
        {
            performTransactions();
        }
    }

    public  void authenticateUser(){
        int pin;
        this.screen.displayMessage("Please enter your account number:");
        this.currentAccountNUmber = this.keypad.getInput();
        this.screen.displayMessage("\nEnter your PIN:");
        pin = this.keypad.getInput();
        this.screen.displayMessageLine("");
        this.userAuthenticated = this.database.authenticateUser(this.currentAccountNUmber,pin);
    }

    public void performTransactions() {
        this.screen.displayMessageLine( "Welcome!");

        while ( !this.userAuthenticated)
        {
            this.authenticateUser();
            if( !this.userAuthenticated)
                this.screen.displayMessageLine("Invalid account number or PIN. Please try again.\n");
        }

        int userChoice;
        do {
            userChoice = this.displayMainMenu();
            switch ( userChoice)
            {
                case 1:
                case 2:
                case 3:
                case 4:
                    Transaction currentTransaction = createTransaction(userChoice);
                    if( currentTransaction != null)
                        currentTransaction.execute();
                    else
                        this.screen.displayMessageLine("\nExiting the system...");
                    break;
                default:
                    this.screen.displayMessageLine("You did not enter a valid selection. Try again.");
            }

        }while( userChoice == 0 || userChoice > 4);

        this.currentAccountNUmber = 0;
        this.userAuthenticated = false;
        this.screen.displayMessageLine( "Thank you! Goodbye...!");
    }

    public int displayMainMenu(){
        this.screen.displayMessageLine( """
                
                ---Main menu---
                1 - View my balance
                2 - Withdraw cash
                3 - Deposit funds
                4  - Exit
                
                Enter a choice: """);
        return  this.keypad.getInput();
    }

    public Transaction createTransaction( int type) {
        switch ( type)
        {
            case 1: return  new BalanceInquiry(this.currentAccountNUmber, this.screen, this.database);
            case 2: return  new Withdrawal(this.currentAccountNUmber, this.screen, this.database,
                    this.keypad, this.cashDispenser);

            case 3: return  new Deposit(this.currentAccountNUmber, this.screen, this.database, this.keypad,
                    this.depositSlot);
            default:
                return null;
        }
    }

}
