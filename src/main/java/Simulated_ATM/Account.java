package Simulated_ATM;

public class Account {

    private int accountNUmber;
    private int pin;
    private double availableBalance;
    private double totalBalance;

    public Account(int accountNUmber, int pin,
                   double availableBalance, double totalBalance) {
        this.accountNUmber = accountNUmber;
        this.pin = pin;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }

    public boolean validatePin(int userPin){
        return this.pin == userPin;
    }

    public double getAvailableBalance(){
        return  availableBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public  int getAccountNUmber(){
        return accountNUmber;
    }

    public void credit( double amount){
        this.totalBalance += amount;
    }

    public  void debit( double amount) {
        this.totalBalance -= amount;
        this.availableBalance -= amount;
    }
}
