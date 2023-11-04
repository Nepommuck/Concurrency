package lab4.task1;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task1 {
    public static void main(String[] args) {
        final int BUFFER_SIZE = 100;
        final int NUMBER_OF_PROCESSORS = 5;
        final double LOG_EVERY_SECONDS = 0.2;

        final Buffer buffer = new Buffer(BUFFER_SIZE, NUMBER_OF_PROCESSORS);

        final Producer producer = new Producer(buffer);
        final Consumer consumer = new Consumer(buffer);
        final Stream<Processor> processors = IntStream.range(0, NUMBER_OF_PROCESSORS)
          .boxed()
          .map(i -> new Processor(buffer, i));

        final BufferLogger logger = new BufferLogger(buffer, LOG_EVERY_SECONDS);

        Stream.concat(processors, Stream.of(producer, consumer, logger))
          .map(Thread::new)
          .forEach(Thread::start);
    }
}
