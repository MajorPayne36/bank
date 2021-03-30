package actors;

import utils.Transaction;

public class Bank {
    private int bankBalance;

    public Bank (int bankBalance){
        this.bankBalance = bankBalance;
    }

    public int getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(int bankBalance) {
        this.bankBalance = bankBalance;
    }
}