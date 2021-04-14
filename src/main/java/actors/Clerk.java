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
    public void run() {
        for (; ; ) {

            // If no clients in queue
            synchronized (clientList) {
                if (clientList.isEmpty()) {
                    try {
                        clientList.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Client currentClient = clientList.get(0);
                doTransaction(currentClient);

                // Wait client time
                try {
                    System.out.println("~~~~~~Время ожидании: " + currentClient.getTimeMS() + "\n");
                    clientList.wait(currentClient.getTimeMS());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clientList.notifyAll(); // Notify other threads locked on this clerk.
            }
        }
    }

    /**
     * Do clients transaction
     *
     * @param client which want to do transaction
     */
    public void doTransaction(Client client) {
        synchronized (bank) {


            switch (client.getTs()) {
                case INSERT:

                    bank.setBankBalance(bank.getBankBalance() + client.getBalance());

                    // Print to console transaction
                    System.out.println(Thread.currentThread().getName() + " внесено " + client.getBalance());
                    System.out.println("~~~~~~Обслуживается клиент: " + client.toString());
                    System.out.println("~~~~~~В банке $: " + bank.getBankBalance());
                    System.out.println("~~~~~~В очереди: " + getClientsCount());

                    // Delete current client from queue
                    clientList.remove(0);

                    break;

                case TAKE:

                    if (bank.getBankBalance() < client.getBalance()) {
                        // Send client to end of queue
                        clientList.remove(0);
                        clientList.add(client);

                        // Print to console transaction
                        System.out.println(Thread.currentThread().getName() + " не хватило денег, подходите позже " + client.getBalance());
                        System.out.println("~~~~~~Обслуживается клиент: " + client.toString());
                        System.out.println("~~~~~~В банке $: " + bank.getBankBalance());
                        System.out.println("~~~~~~В очереди: " + getClientsCount());

                    } else {
                        bank.setBankBalance(bank.getBankBalance() - client.getBalance());

                        // Print to console transaction
                        System.out.println(Thread.currentThread().getName() + " снято " + client.getBalance());
                        System.out.println("~~~~~~Обслуживается клиент: " + client.toString());
                        System.out.println("~~~~~~В банке $: " + bank.getBankBalance());
                        System.out.println("~~~~~~В очереди: " + getClientsCount());

                        // Delete current client from queue
                        clientList.remove(0);

                    }
                    break;

                default:
                    System.out.println("Invalid transaction");
            }
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
        synchronized (clientList) {
            clientList.add(client);
            clientList.notify();
        }
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName();
    }
}