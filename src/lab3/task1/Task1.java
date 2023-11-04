package lab3.task1;

import java.util.List;
import java.util.stream.IntStream;

public class Task1 {
    public static void main(String[] args) {
        final int NUMBER_OF_PRINTERS = 2;
        final int NUMBER_OF_CONSUMERS = 9;

        final PrinterMonitor printerMonitor = new PrinterMonitor(NUMBER_OF_PRINTERS);

        final List<Thread> consumers = IntStream.range(0, NUMBER_OF_CONSUMERS)
          .boxed()
          .map(i -> new ConsumerThread(printerMonitor, i))
          .map(Thread::new)
          .toList();

        consumers.forEach(Thread::start);
    }
}
