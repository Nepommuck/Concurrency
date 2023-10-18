package lab2.task2;

import java.util.List;
import java.util.stream.IntStream;

public class Task2 {
    public static void main(String[] args) {
        final Shop shop = new Shop(3);

        List<Thread> consumerThreads = IntStream.range(0, 10)
          .boxed()
          .map(i -> new Customer(shop, i))
          .map(Thread::new)
          .toList();

        List<Thread> workerThreads = IntStream.range(0, 3)
          .boxed()
          .map(i -> new Worker(shop))
          .map(Thread::new)
          .toList();

        shop.start();
        consumerThreads.forEach(Thread::start);
        workerThreads.forEach(Thread::start);
    }
}
