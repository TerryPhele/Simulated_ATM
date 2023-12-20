package Simulated_ATM;

public class BalanceInquiry extends  Transaction{

    public BalanceInquiry( int userAccountNumber, Screen atmScreen, Database bankDatabase)
    {
        super(userAccountNumber,atmScreen, bankDatabase);
    }

    @Override
    public void execute() {
        double availableBalance = this.getBankDataBase().getAccount( this.getAccountNumber()).getAvailableBalance();
        double totalBalance = this.getBankDataBase().getAccount( this.getAccountNumber()).getTotalBalance();


        this.getScreen().displayMessageLine("---Balance information---");
        this.getScreen().displayMessage("- Available balance: ");
        this.getScreen().displayAmount(availableBalance);
        this.getScreen().displayMessage("- Total balance: ");
        this.getScreen().displayAmount(totalBalance);
        this.getScreen().displayMessageLine("=============================");
    }
}
