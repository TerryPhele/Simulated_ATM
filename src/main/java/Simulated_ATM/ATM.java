package Simulated_ATM;

import java.io.InputStream;

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
    public ATM(InputStream input, Database bankDatabse) {
        userAuthenticated = false;
        currentAccountNUmber = 0;
        screen = new Screen();
        keypad = new Keypad( input);
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        database = bankDatabse;
    }

    public boolean getUserAuthenticated()
    {
        return  this.userAuthenticated;
    }

    public  void run(){

    }

    public  void authenticateUser(){

    }

    public void performTransactions() {

    }

    public int displayMainMenu(){
        return  0;
    }

    public Transaction createTransaction( int type) {
        return null;
    }



}
