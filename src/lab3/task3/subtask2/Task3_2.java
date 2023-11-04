package lab3.task3.subtask2;

import lab3.task3.subtask1.Forks;
import lab3.task3.subtask1.Philosopher;

import java.util.List;
import java.util.stream.IntStream;

public class Task3_2 {
    public static void main(String[] args) {
        final int NUMBER_OF_PHILOSOPHERS = 5;

        final Forks forks = new SynchronizedForks(NUMBER_OF_PHILOSOPHERS);

        final List<Thread> philosophers = IntStream
          .range(0, NUMBER_OF_PHILOSOPHERS)
          .boxed()
          .map(i -> new Philosopher(i, forks))
          .map(Thread::new)
          .toList();

        philosophers.forEach(Thread::start);
    }
}
