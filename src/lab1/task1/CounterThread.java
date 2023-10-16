package lab1.task1;

public class CounterThread extends Thread {
    private final Counter counter;
    private final int incrementer;
    private final int numberOfIterations;

    public CounterThread(Counter counter, int operation) {
        this.counter = counter;
        incrementer = (operation >= 0) ? 1 : -1;
        numberOfIterations = Math.abs(operation);
    }
    @Override
    public void run() {
        super.run();

        for (int i = 0; i < numberOfIterations; i++) {
            if (incrementer > 0)
                counter.increment();
            else
                counter.decrement();
        }
    }
}
