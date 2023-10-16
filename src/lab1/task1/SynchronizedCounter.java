package lab1.task1;

public class SynchronizedCounter extends Counter {
    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public synchronized void decrement() {
        value--;
    }
}
