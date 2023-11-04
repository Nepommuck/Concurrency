package lab3.task3.subtask1;

import common.TimeLogger;
import lab3.task3.common.Direction;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Forks {
    protected final int numberOfForks;
    protected final Lock lock = new ReentrantLock();
    protected final List<Condition> forkConditions;
    protected final List<Boolean> isForkFree;

    public Forks(int numberOfForks) {
        this.numberOfForks = numberOfForks;

        this.forkConditions = IntStream
          .range(0, numberOfForks)
          .boxed()
          .map(i -> lock.newCondition())
          .collect(Collectors.toList());

        this.isForkFree = IntStream
          .range(0, numberOfForks)
          .boxed()
          .map(i -> true)
          .collect(Collectors.toList());
    }

    protected boolean isForkFree(int forkIndex) {
        return isForkFree.get(forkIndex);
    }

    protected int getForkIndex(int philosopherNumber, Direction direction) {
        int delta = switch (direction) {
            case Left -> 0;
            case Right -> 1;
        };
        return (philosopherNumber + delta) % numberOfForks;
    }

    protected void log(int philosopherNumber, String message) {
        TimeLogger.log("Philosopher " + philosopherNumber + ": " + message);
    }

    public void take(int philosopherNumber, Direction whichFork) {
        final int forkIndex = getForkIndex(philosopherNumber, whichFork);

        try {
            lock.lock();
            while (!isForkFree(forkIndex)) {
                try {
                    forkConditions.get(forkIndex).await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Fork is taken
            isForkFree.set(forkIndex, false);
            log(philosopherNumber, "Fork " + forkIndex + " taken");
        } finally {
            lock.unlock();
        }
    }


    public void returnFork(int philosopherNumber, Direction whichFork) {
        final int forkIndex = getForkIndex(philosopherNumber, whichFork);

        lock.lock();
        try {
            if (isForkFree(forkIndex))
                throw new IllegalStateException("Fork " + forkIndex + " is already free");

            // Fork is returned
            isForkFree.set(forkIndex, true);
            log(philosopherNumber, "Fork " + forkIndex + " returned");
            forkConditions.get(forkIndex).signal();
        } finally {
            lock.unlock();
        }
    }
}
