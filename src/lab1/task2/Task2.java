package lab1.task2;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Task2 {
    public static void main(String[] args) {
        int numberOfThreads = 3;
        int numberOfMessages = 2;

        Buffer buffer = new Buffer();

        List<Thread> producerThreads = IntStream.range(0, numberOfThreads)
                .boxed()
                .map(i -> new Producer(buffer, numberOfMessages, Optional.of("PRODUCER " + i)))
                .map(Thread::new)
                .toList();

        List<Thread> consumerThreads = IntStream.range(0, numberOfThreads)
                .boxed()
                .map(i -> new Consumer(buffer, numberOfMessages, Optional.of("CONSUMER " + i)))
                .map(Thread::new)
                .toList();

        producerThreads.forEach(Thread::start);
        consumerThreads.forEach(Thread::start);
    }
}
