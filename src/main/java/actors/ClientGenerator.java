package actors;

import utils.Transaction;

import java.util.Random;

public class ClientGenerator implements Runnable {
    private Clerk clerk1;
    private Clerk clerk2;
    private Clerk clerk3;

    public ClientGenerator (Clerk clerk1, Clerk clerk2, Clerk clerk3) {
        this.clerk1 = clerk1;
        this.clerk2 = clerk2;
        this.clerk3 = clerk3;
    }
    /**
     * Generate clients with operation type, balance, and the time which use Clerk
     */
    synchronized public void run(){
        Random random = new Random();
        int number = 0;
        Client newClient;

        // Create random client
        for (; ; ) {
            Transaction ts;
            int value = random.nextInt(10) * 1000;
            if (random.nextInt(2) == 1) {
                newClient = new Client(
                        value,
                        Transaction.INSERT,
                        value,
                        Integer.toString(number++)
                );
            } else {
                newClient = new Client(
                        value,
                        Transaction.TAKE,
                        value,
                        Integer.toString(number++)
                );
            }

            // Send to clerk queue
            Clerk clerk = getClerkQueue();
            clerk.addClient(newClient);

            // Print thread name
            System.out.println(Thread.currentThread().getName());

            // Wait 5 sec
            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Clerk getClerkQueue () {
        if (clerk1.getClientsCount() <= clerk2.getClientsCount() &&
                clerk1.getClientsCount() <= clerk3.getClientsCount()) {
            return clerk1;
        } else if (clerk2.getClientsCount() <= clerk1.getClientsCount() &&
                clerk2.getClientsCount() <= clerk3.getClientsCount()) {
            return clerk2;
        } else {
            return clerk3;
        }
    }

}
