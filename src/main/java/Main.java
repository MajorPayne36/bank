import actors.Bank;
import actors.Clerk;
import actors.ClientGenerator;

public class Main {
    // Create Bank with start balance=0
    private static Bank bank = new Bank(0);

    // Add clerks in bank
    private static Clerk clerk1 = new Clerk(bank);
    private static Clerk clerk2 = new Clerk(bank);
    private static Clerk clerk3 = new Clerk(bank);

    public static void main(String[] args) {

        ClientGenerator generator = new ClientGenerator(clerk1,clerk2,clerk3);

        // Create the threads for the clerks
        Thread clerk1Thread = new Thread(clerk1);
        Thread clerk2Thread = new Thread(clerk2);
        Thread clerk3Thread = new Thread(clerk3);
        Thread clentGeneratorThread = new Thread(generator);

        // Set thread names
        clerk1Thread.setName("1) поток первого клиента");
        clerk2Thread.setName("2) поток второго клиента");
        clerk3Thread.setName("3) поток третего клиента");
        clentGeneratorThread.setName("CLIENT WAS GENERATED");

        // Start threads
        clerk1Thread.start();
        clerk2Thread.start();
        clerk3Thread.start();
        clentGeneratorThread.start();

    }
}