package lab3.task2;

import common.TimeLogger;
import common.ThreadRandomSleep;

public class Client implements Runnable {
    private final int pairNumber;
    private final Waiter waiter;

    public Client(int pairNumber, Waiter waiter) {
        this.pairNumber = pairNumber;
        this.waiter = waiter;
    }

    public int getPairNumber() {
        return pairNumber;
    }

    private void doPrivateStuff() {
        ThreadRandomSleep.sleep(2, 15);
    }

    private void eat() {
        TimeLogger.log("Client from pair " + pairNumber + " starts eating");
        ThreadRandomSleep.sleep(3, 4);
        TimeLogger.log("Client from pair " + pairNumber + " finished eating");
    }

    @Override
    public void run() {
        while (true) {
            doPrivateStuff();
            TimeLogger.log("Client from pair " + pairNumber + " is ready to eat");
            waiter.reserveTable(this);
            eat();
            waiter.freeTable();
        }
    }
}
