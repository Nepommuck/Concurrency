package lab2.task1;

import lab1.task1.Counter;
import lab1.task1.CounterThread;


public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        int NUMBER_OF_OPERATIONS = 100_000_000;
        Counter semaphoreCounter = new BinarySemaphoreCounter();

        Thread thread1 = new CounterThread(semaphoreCounter, +NUMBER_OF_OPERATIONS);
        Thread thread2 = new CounterThread(semaphoreCounter, -NUMBER_OF_OPERATIONS);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Counter after operations: " + semaphoreCounter.getValue());
    }
}
