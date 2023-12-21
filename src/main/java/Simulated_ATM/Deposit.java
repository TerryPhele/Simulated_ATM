package Simulated_ATM;

public class Deposit  extends  Transaction{
    private double amount;
    private  Keypad keypad;
    private  DepositSlot depositSlot;
    private  final  static  int depositCancelled  = 0;

    public  Deposit( int userAccount, Screen atmScreen, Database bankDatabase, Keypad atmKeypad,
                     DepositSlot atmDepositSlot)
    {
        super(userAccount, atmScreen,bankDatabase);
        this.keypad = atmKeypad;
        this.depositSlot = atmDepositSlot;
    }

    @Override
    public void execute() {
        this.amount = promptForDeposition();
        if( amount != depositCancelled)
        {
            this.getScreen().displayMessage("\nPlease insert R"+ (int)amount + " on the depositSlot.\n" );
            this.getBankDataBase().getAccount( this.getAccountNumber()).credit( amount);
            this.getScreen().displayMessageLine("\nYour money has been received.\n");

        }else{
            this.getScreen().displayMessageLine("\nCanceling transaction...");
        }
    }

    public  double promptForDeposition()
    {
        this.getScreen().displayMessage("Enter the amount to deposit (or 0 to cancel):");
        this.getScreen().displayMessageLine("");
        return (double) (this.keypad.getInput());
    }
}
