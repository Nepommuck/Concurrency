package lab3.task2;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task2 {
    public static void main(String[] args) {
        final int NUMBER_OF_PAIRS = 3;

        final Waiter waiter = new Waiter(NUMBER_OF_PAIRS);
        final List<Thread> consumers = IntStream.range(0, NUMBER_OF_PAIRS)
          .boxed()
          .flatMap(i -> Stream.of(i, i))
          .map(pairNumber -> new Client(pairNumber, waiter))
          .map(Thread::new)
          .toList();

        consumers.forEach(Thread::start);
    }
}
