package actors;

import utils.Transaction;

public class Client {

    /**
     * Current client balance, which we use to do transaction in the bank
     */
    private int balance;

    /**
     * Time how clerk wait while do transaction
     */
    private int timeMS;

    /**
     * Client number
     */
    private String clientNumber;

    /**
     * Client transaction type
     */
    private Transaction ts;

    /**
     *
     * @param balance the initial balance
     * @param ts transaction
     * @param timeMS waiting time in ms
     * @param clientNumber account number
     */
    public Client(int balance, Transaction ts, int timeMS,  String clientNumber) {
        this.balance = balance;
        this.ts = ts;
        this.timeMS = timeMS;
        this.clientNumber = clientNumber;
    }

    /**
     * @return current client balance, which we use to do transaction in the bank
     */
    public int getBalance() {
        return balance;

    }

    /**
     * @return waiting time
     */
    public int getTimeMS() {
        return timeMS;
    }

    public Transaction getTs() {
        return ts;
    }

    public String toString() {
        return "No. " + clientNumber + " : $" + balance;
    }
}