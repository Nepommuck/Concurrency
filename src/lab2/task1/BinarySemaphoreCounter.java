package lab2.task1;

import lab1.task1.Counter;

public class BinarySemaphoreCounter extends Counter {
    private final BinarySemaphore semaphore = new BinarySemaphore();
    @Override
    public void increment() {
        semaphore.lock();
        value++;
        semaphore.release();
    }

    @Override
    public void decrement() {
        semaphore.lock();
        value--;
        semaphore.release();
    }
}
