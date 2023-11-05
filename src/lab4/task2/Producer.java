package lab4.task2;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Producer extends Actor {
    public Producer(int numberOfElements, Buffer buffer, DataCollector dataCollector) {
        super(numberOfElements, buffer, dataCollector);
    }

    @Override
    protected void act() {
        final var newElements = IntStream.range(0, numberOfElements)
          .boxed()
          .map(i -> new Element())
          .collect(Collectors.toSet());

        buffer.put(newElements);
    }

    @Override
    protected void notifyDataCollector(long totalTime) {
        dataCollector.addProducerReading(numberOfElements, totalTime);
    }
}
