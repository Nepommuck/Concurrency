package lab2.task2;

import lab2.Semaphore;

public class CountingSemaphore implements Semaphore {
    private int freeSlots;

    public CountingSemaphore(final int initialSlots) {
        freeSlots = initialSlots;
    }

    public int getCurrentFreeSlots() {
        return freeSlots;
    }

    @Override
    public synchronized void release() {
        freeSlots++;
        this.notify();
    }

    @Override
    public synchronized void lock() {
        while (freeSlots <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        freeSlots--;
    }
}
