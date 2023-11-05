package lab4.task2;

public class Consumer extends Actor {
    public Consumer(int numberOfElements, Buffer buffer, DataCollector dataCollector) {
        super(numberOfElements, buffer, dataCollector);
    }

    @Override
    protected void act() {
        buffer.get(numberOfElements);
    }

    @Override
    protected void notifyDataCollector(long totalTime) {
        dataCollector.addConsumerReading(numberOfElements, totalTime);
    }
}
