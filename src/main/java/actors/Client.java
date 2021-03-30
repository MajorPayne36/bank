package actors;

import utils.Transaction;

public class Client {

    private int balance; // The current account balance.
    private int timeMS;
    private String accountNumber;
    private Transaction ts;

    /**
     *
     * @param balance the initial balance
     * @param ts transaction
     * @param timeMS waiting time in ms
     * @param accountNumber account number
     */
    public Client(int balance, Transaction ts, int timeMS,  String accountNumber) {
        this.balance = balance;
        this.ts = ts;
        this.timeMS = timeMS;
        this.accountNumber = accountNumber;
    }

    /**
     *
     * @return the transaction balance
     */
    public int getBalance() {
        return balance;

    }

    /**
     *
     * @return waiting time
     */
    public int getTimeMS() {
        return timeMS;
    }

    public Transaction getTs() {
        return ts;
    }

    public String toString() {
        return "A//C No. " + accountNumber + " : $" + balance;
    }
}