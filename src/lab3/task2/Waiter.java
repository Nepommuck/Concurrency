package lab3.task2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Waiter {
    private Optional<Integer> currentPair = Optional.empty();
    private int peopleSitting = 0;
    private final Map<Integer, Integer> pairWaitingMap = new HashMap<>();

    private final Lock lock = new ReentrantLock();
    private final Condition tableAvailable = lock.newCondition();
    private final List<Condition> pairConditions;

    public Waiter(int numberOfPairs) {
        this.pairConditions = IntStream.range(0, numberOfPairs)
          .boxed()
          .map(i -> lock.newCondition())
          .toList();
    }

    public void reserveTable(Client client) {
        lock.lock();
        try {
            final int pairNumber = client.getPairNumber();
            pairWaitingMap.put(
              pairNumber,
              pairWaitingMap.getOrDefault(pairNumber, 0) + 1
            );

            if (pairWaitingMap.get(pairNumber) < 2)
                pairConditions.get(pairNumber).await();
            else {
                while (currentPair.isPresent()) {
                    tableAvailable.await();
                }
                currentPair = Optional.of(pairNumber);
                peopleSitting = 2;

                pairConditions.get(pairNumber).signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void freeTable() {
        lock.lock();
        try {
            peopleSitting -= 1;
            if (peopleSitting == 0) {
                pairWaitingMap.put(currentPair.get(), 0);
                currentPair = Optional.empty();
                tableAvailable.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
