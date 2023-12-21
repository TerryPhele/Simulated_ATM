package Simulated_ATM;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<Account> accountList;

    //for testing...ignore
    public  Database( List<Account> accountList){
        Database.accountList = accountList;
    }

    //for running the program...(not testing)
    public  Database(){
        accountList = new ArrayList<>();
        // just for successfully running the app.....//for manual testing
        //!atleast database has to have data.
        accountList.add(new Account(223456789, 2234,
                100,100));
        accountList.add( new Account(123456789, 1234,
                200,200));
    }



    public Account getAccount( int accountNumber){
        Account temp = null;
        for( Account currentAccount : Database.accountList)
        {
            temp = accountNumber == currentAccount.getAccountNUmber()? currentAccount: null;
            if( temp != null)
                break;
        }
        return temp;
    }

    public boolean authenticateUser( int accountNumber, int userPin){
        return getAccount(accountNumber) != null && getAccount(accountNumber).validatePin(userPin);
    }

    public double getAvailableBalance( int accountNumber){
        return getAccount(accountNumber).getAvailableBalance();
    }
    public double getTotalBalance( int accountNumber){
        return getAccount(accountNumber).getTotalBalance();
    }
    public void credit( int accountNumber, double amount){
        getAccount(accountNumber).credit(amount);
    }
    public void debit(int accountNumber, double amount){
        getAccount(accountNumber).debit(amount);
    }
}
