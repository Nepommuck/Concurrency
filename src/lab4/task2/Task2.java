package lab4.task2;

import common.TimeLogger;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task2 {
    public static void main(String[] args) {
//        final int M = 1_000;
//        final int M = 10_000;
        final int M = 100_000;

//        final int NUMBER_OF_ACTORS = 10;
        final int NUMBER_OF_ACTORS = 100;
//        final int NUMBER_OF_ACTORS = 1_000;

        final String BUFFER_NAME = "Naive Buffer";
        final Function<Integer, Buffer> NEW_BUFFER_INSTANCE = Buffer::new;

//        final String BUFFER_NAME = "Just Buffer";
//        final Function<Integer, Buffer> NEW_BUFFER_INSTANCE = BetterBuffer::new;

        final String title = BUFFER_NAME + "; M = " + M + "; " + NUMBER_OF_ACTORS + " actors";
        testScenario(M, NUMBER_OF_ACTORS, NEW_BUFFER_INSTANCE, title);
    }

    public static void testScenario(
      final int m,
      final int numberOfActors,
      final Function<Integer, Buffer> newBufferInstance,
      String plotTitle
    ) {
        final Random random = new Random();
        final var dataCollector = new DataCollector();
        final var buffer = newBufferInstance.apply(2 * m);

        final var threads = IntStream.range(0, numberOfActors)
          .boxed()
          .flatMap(i -> {
                int randomNumber = random.nextInt(1, m + 1);
                return Stream.of(
                  new Producer(randomNumber, buffer, dataCollector),
                  new Consumer(randomNumber, buffer, dataCollector)
                );
            }
          )
          // Shuffle all threads
          .sorted(Comparator.comparingInt(Object::hashCode))
          .map(Thread::new)
          .toList();

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        final var producerResults = dataCollector.getProducerAverages();
        final var consumerResults = dataCollector.getConsumerAverages();

        TimeLogger.log(producerResults.toString());
        TimeLogger.log(consumerResults.toString());

        Stream.of(
            new Plotter(
              producerResults,
              "Producers - " + plotTitle,
              "Number of elements",
              "Average time elapsed [ms]"
            ),
            new Plotter(
              consumerResults,
              "Consumers - " + plotTitle,
              "Number of elements",
              "Average time elapsed [ms]"
            )
          ).map(Thread::new)
          .forEach(Thread::start);
    }
}
