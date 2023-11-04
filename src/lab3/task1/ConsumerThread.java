package lab3.task1;

import java.time.LocalTime;

public class ConsumerThread implements Runnable {
    private final PrinterMonitor printerMonitor;
    private final String name;

    public ConsumerThread(PrinterMonitor printerMonitor, int id) {
        this.printerMonitor = printerMonitor;
        this.name = "Thread " + (id + 1);
    }

    @Override
    public void run() {
        while (true) {
            String messageToPrint = this.name + " requested to print this at " + LocalTime.now();
            Printer availablePrinter = printerMonitor.reserve();
            availablePrinter.print(messageToPrint);
            printerMonitor.release(availablePrinter);
        }
    }
}
