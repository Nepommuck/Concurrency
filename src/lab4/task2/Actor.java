package lab4.task2;

public abstract class Actor implements Runnable {
    final int numberOfElements;
    final Buffer buffer;
    final DataCollector dataCollector;

    public Actor(int numberOfElements, Buffer buffer, DataCollector dataCollector) {
        this.numberOfElements = numberOfElements;
        this.buffer = buffer;
        this.dataCollector = dataCollector;
    }

    protected abstract void act();

    protected abstract void notifyDataCollector(long time);

    @Override
    public void run() {
        final long startTime = System.nanoTime();
        act();

        final long totalTime = System.nanoTime() - startTime;
        notifyDataCollector(totalTime);
    }
}
