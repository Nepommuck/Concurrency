package lab3.task3.subtask1;

import common.ThreadSleep;
import common.TimeLogger;

import static lab3.task3.common.Direction.Left;
import static lab3.task3.common.Direction.Right;

public class Philosopher implements Runnable {
    private final int philosopherNumber;
    private final Forks forks;

    public Philosopher(int philosopherNumber, Forks forks) {
        this.philosopherNumber = philosopherNumber;
        this.forks = forks;
    }

    private void doPrivateStuff() {
        ThreadSleep.randomSleep(0.1, 1);
    }

    private void eat() {
        TimeLogger.log("Philosopher " + philosopherNumber + " starts eating");
        ThreadSleep.randomSleep(1, 2);
        TimeLogger.log("Philosopher " + philosopherNumber + " finished eating");
    }

    @Override
    public void run() {
        while (true) {
            doPrivateStuff();
            forks.take(philosopherNumber, Left);
            ThreadSleep.randomSleep(0.3, 1);
            forks.take(philosopherNumber, Right);

            eat();
            forks.returnFork(philosopherNumber, Left);
            forks.returnFork(philosopherNumber, Right);
        }
    }
}
