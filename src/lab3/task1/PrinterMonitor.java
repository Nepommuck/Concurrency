package lab3.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class PrinterMonitor {
    private final List<Printer> availablePrinters;
    private final List<Printer> busyPrinters;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();

    PrinterMonitor(int numberOfPrinters) {
        List<Printer> initialPrinters = IntStream.range(0, numberOfPrinters)
          .boxed()
          .map(Printer::new)
          .toList();

        this.availablePrinters = new ArrayList<>(initialPrinters);
        this.busyPrinters = new ArrayList<>();
    }

    public Printer reserve() {
        lock.lock();
        try {
            while (availablePrinters.size() == 0)
                notFull.await();

            Printer reservedPrinter = availablePrinters.remove(0);
            busyPrinters.add(reservedPrinter);

            return reservedPrinter;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
    }

    public void release(Printer printer) {
        lock.lock();
        try {
            if (!busyPrinters.contains(printer))
                throw new IllegalArgumentException("Printer was not busy.");
            busyPrinters.remove(printer);
            availablePrinters.add(printer);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
