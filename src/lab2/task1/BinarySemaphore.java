package lab2.task1;

import lab2.Semaphore;

import static lab2.task1.BinarySemaphore.SemaphoreState.CLOSED;
import static lab2.task1.BinarySemaphore.SemaphoreState.OPEN;

public class BinarySemaphore implements Semaphore {
    enum SemaphoreState {OPEN, CLOSED}

    private SemaphoreState state = OPEN;

    @Override
    public synchronized void release() {
        if (state == OPEN)
            throw new RuntimeException("Semaphore already opened");

        state = OPEN;
        this.notify();
    }

    @Override
    public synchronized void lock() {
        while (state == CLOSED) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        state = CLOSED;
    }
}
