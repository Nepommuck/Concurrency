package lab3.task3.subtask2;

import lab3.task3.common.Direction;
import lab3.task3.subtask1.Forks;

import java.util.concurrent.locks.Condition;

public class SynchronizedForks extends Forks {
    private int currentlyWaitingPhilosophers = 0;
    private final int maxWaitingPhilosophers;
    private final Condition toManyWaitingPhilosophers = lock.newCondition();

    public SynchronizedForks(int numberOfForks) {
        this(numberOfForks, numberOfForks - 1);
    }

    public SynchronizedForks(int numberOfForks, int maxWaitingPhilosophers) {
        super(numberOfForks);
        this.maxWaitingPhilosophers = maxWaitingPhilosophers;
    }

    @Override
    public void take(int philosopherNumber, Direction whichFork) {
        final int forkIndex = getForkIndex(philosopherNumber, whichFork);
        try {
            lock.lock();

            // Wait with taking first fork if (n-1) philosophers are already waiting
            while (whichFork == Direction.Left && currentlyWaitingPhilosophers >= maxWaitingPhilosophers) {
                try {
                    toManyWaitingPhilosophers.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // It is the first fork that the philosopher tries to take
            if (whichFork == Direction.Left)
                currentlyWaitingPhilosophers += 1;

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

            if (whichFork == Direction.Right) {
                currentlyWaitingPhilosophers -= 1;
                toManyWaitingPhilosophers.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
