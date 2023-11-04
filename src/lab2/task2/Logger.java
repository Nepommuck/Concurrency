package lab2.task2;

import common.TimeLogger;

public class Logger {
    public static void log(String name, String message) {
        TimeLogger.log(
          String.format("%10s: %s", name, message)
        );
    }
}
