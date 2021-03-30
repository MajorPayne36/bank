package actors;

import java.util.*;

public class Clerk implements Runnable {
    private List<Client> clientList = new ArrayList<>();
    private Bank bank;

    public Clerk(Bank bank) {
        this.bank = bank;
    }

    /**
     * Do transaction or wait
     */
    synchronized public void run() {
        for (; ; ) {
            while (clientList.isEmpty()) { // No clients waiting?
                try {
                    wait(); // Then take a break until there is.
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            Client currentClient = clientList.get(0);
            doTransaction(currentClient);

            // Wait client time
            try {
                System.out.println(Thread.currentThread().getName()+ " время ожидании: " + currentClient.getTimeMS());
                wait(currentClient.getTimeMS());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll(); // Notify other threads locked on this clerk.
        }
    }

    /**
     * Do clients transaction
     *
     * @param client which want to do transaction
     */
    public void doTransaction(Client client) {
        switch (client.getTs()) {
            case INSERT:
                synchronized (bank) {
                    bank.setBankBalance(bank.getBankBalance() + client.getBalance());

                    // Print to console transaction
                    System.out.println(Thread.currentThread().getName()+" внесено " + client.getBalance());
                    System.out.println("~~~~~~В банке $: " + bank.getBankBalance());
                    System.out.println("~~~~~~В очереди: " + getClientsCount());

                    break;
                }
            case TAKE:
                synchronized (bank) {
                    if (bank.getBankBalance() < client.getBalance()) {
                        // Send client to end of queue
                        clientList.remove(0);
                        clientList.add(client);

                        // Print to console transaction
                        System.out.println(Thread.currentThread().getName()+" не хватило денег, подходите позже " + client.getBalance());
                        System.out.println("~~~~~~В банке $: " + bank.getBankBalance());
                        System.out.println("~~~~~~В очереди: " + getClientsCount());

                    } else {
                        bank.setBankBalance(bank.getBankBalance() - client.getBalance());

                        // Print to console transaction
                        System.out.println(Thread.currentThread().getName()+" снято " + client.getBalance());
                        System.out.println("~~~~~~В банке $: " + bank.getBankBalance());
                        System.out.println("~~~~~~В очереди: " + getClientsCount());

                    }
                    // Increment the balance. transaction.getAccount().setBalance(balance);
                    // Restore account balance.
                    break;
                }
            default:
                System.out.println("Invalid transaction");
        }
    }

    /**
     * @return count of clients in current clerk queue
     */
    public int getClientsCount() {
        return this.clientList.size();
    }

    /**
     * @param client will added to this clerk queue
     */
    public void addClient(Client client) {
        clientList.add(client);
    }
}