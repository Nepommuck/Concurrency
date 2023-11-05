package lab4.task2;

import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BetterBuffer extends Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition moreThanHalf = lock.newCondition();
    private final Condition lessThanHalf = lock.newCondition();

    public BetterBuffer(int size) {
        super(size);
        put(
          IntStream.range(0, size / 2)
            .boxed()
            .map(i -> new Element())
            .collect(Collectors.toUnmodifiableSet())
        );
    }

    @Override
    public void put(Set<Element> newElements) {
        lock.lock();
        try {
            while (currentNumberOfElements() > bufferSize / 2)
                moreThanHalf.await();

            lock.unlock();
            elements.addAll(newElements);
            lock.lock();

            lessThanHalf.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<Element> get(int numberOfElements) {
        lock.lock();
        try {
            while (currentNumberOfElements() < bufferSize / 2)
                lessThanHalf.await();

            final var result = elements.pop(numberOfElements);

            moreThanHalf.signalAll();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
