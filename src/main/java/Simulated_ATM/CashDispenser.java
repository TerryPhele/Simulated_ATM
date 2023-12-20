package Simulated_ATM;

public class CashDispenser {

    private final static int INITIAL_BILLS_NOTES_COUNT= 500; //(R20 x 500 bills)
    private int numberBillsNotes;

    public CashDispenser() {
        numberBillsNotes = INITIAL_BILLS_NOTES_COUNT;
    }

    public void dispenseCash( int amount_to_dispense){
        numberBillsNotes -= (amount_to_dispense/20);
    }
    public boolean isSufficientCashAvailable( int amount_to_dispense){
        return  numberBillsNotes >= ( amount_to_dispense/20);
    }

    public int getNumberOfBillsNotesRemaining() {
        return numberBillsNotes;
    }
}
