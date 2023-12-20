package Simulated_ATM;

public abstract class Transaction {
    private int accountNumber;
    private Screen screen;
    private Database bankDataBase;

    public  Transaction(int userAccountNumber, Screen atmScreen, Database bankDatabase)
    {
        this.accountNumber = userAccountNumber;
        this.screen = atmScreen;
        this.bankDataBase = bankDatabase;
    }
    public int getAccountNumber() {
       return accountNumber;
    }

    public Screen getScreen() {
        return screen;
    }

    public Database getBankDataBase() {
        return bankDataBase;
    }

    public abstract void execute();
}
