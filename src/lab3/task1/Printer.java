package lab3.task1;

import common.ThreadSleep;
import common.TimeLogger;

public class Printer {
    private final String name;

    public Printer(int id) {
        this.name = "PRINTER " + (id + 1);
    }

    public void print(String message) {
        TimeLogger.log(this.name + ": Starts printing...");
        ThreadSleep.randomSleep(1.5, 5);
        TimeLogger.log(this.name + " >> \"" + message + "\"\n");
    }
}
