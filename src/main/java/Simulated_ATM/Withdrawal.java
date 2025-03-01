package Simulated_ATM;

public class Withdrawal extends Transaction {

    private double amount;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private  final static  int WITHDRAWALCANCELLED = 7;
    private  final static int[] amountsOption =  {20,40,60, 100, 200,1000000, WITHDRAWALCANCELLED};

    public Withdrawal( int userAccountNumber, Screen atmScreen, Database bankDatabase,
                       Keypad atmKeypad, CashDispenser atmCashDispenser)
    {
        super(userAccountNumber, atmScreen, bankDatabase);
        this.keypad = atmKeypad;
        this.cashDispenser = atmCashDispenser;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    @Override
    public void execute() {
        boolean withdrawalSuccess = false;
        double amountAvailableInAccount = this.getBankDataBase().
                getAccount( this.getAccountNumber()).getAvailableBalance();

        do {
            displayMenuOfAmounts();
            int amountToWithdraw;
            this.getScreen().displayMessage("Choose a withdrawal amount:");
            amountToWithdraw = this.getChosenAmountToWithDraw();
            this.getScreen().displayMessageLine("");
            if( amountToWithdraw != WITHDRAWALCANCELLED)
            {
                if( amountToWithdraw <= amountAvailableInAccount)
                {
                    if( this.cashDispenser.isSufficientCashAvailable(amountToWithdraw))
                    {
                        this.getBankDataBase().debit(this.getAccountNumber(), amountToWithdraw);
                        this.cashDispenser.dispenseCash(amountToWithdraw);
                        withdrawalSuccess = true;
                        this.getScreen().displayMessageLine("\nYour cash has been dispensed. Please take your cash now.\n");

                    }else{
                        this.getScreen().displayMessageLine("\nInsufficient cash available in ATM.\n");
                        this.getScreen().displayMessageLine("Please choose a small amount.\n");
                    }

                }else {
                    this.getScreen().displayMessageLine("\nInsufficient funds in your account.\n");
                    this.getScreen().displayMessageLine("Please choose a small amount.\n");
                }

            }else{
                this.getScreen().displayMessageLine("\nCanceling transaction...");
                return;
            }

        }while( !withdrawalSuccess);

    }


    public  void displayMenuOfAmounts()
    {
        this.getScreen().displayMessageLine("""
                ---Withdrawal Menu---
                1 - R20
                2 - R40
                3 - R60
                4 - R100
                5 - R200
                6 - R1000000
                7 - Cancel withdrawal
                """);
    }

    public int getChosenAmountToWithDraw()
    {
        int amountToWithdraw = this.keypad.getInput();
        return  amountsOption[amountToWithdraw-1];
    }


}
