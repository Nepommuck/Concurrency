package lab1.task1;

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        int NUMBER_OF_OPERATIONS = 100_000_000;
        Counter unsynchronizedCounter = new UnsynchronizedCounter();

        Thread thread1 = new CounterThread(unsynchronizedCounter, +NUMBER_OF_OPERATIONS);
        Thread thread2 = new CounterThread(unsynchronizedCounter, -NUMBER_OF_OPERATIONS);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Unsynchronized counter after operations: " + unsynchronizedCounter.getValue());

        Counter synchronizedCounter = new SynchronizedCounter();
        Thread thread3 = new CounterThread(synchronizedCounter, +NUMBER_OF_OPERATIONS);
        Thread thread4 = new CounterThread(synchronizedCounter, -NUMBER_OF_OPERATIONS);

        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();
        System.out.println("Synchronized counter after operations: " + synchronizedCounter.getValue());
    }
}
