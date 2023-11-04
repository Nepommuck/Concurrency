package lab4.task1;

import common.ThreadSleep;
import common.TimeLogger;

public class BufferLogger implements Runnable {
    private final Buffer buffer;
    private final double logEverySeconds;

    public BufferLogger(Buffer buffer, double logEverySeconds) {
        this.buffer = buffer;
        this.logEverySeconds = logEverySeconds;
    }

    @Override
    public void run() {
        while (true) {
            TimeLogger.log(buffer.toString());
            ThreadSleep.sleep(logEverySeconds);
        }
    }
}
