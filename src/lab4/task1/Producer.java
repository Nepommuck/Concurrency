package lab4.task1;

import common.ThreadSleep;

public class Producer extends Actor {
    protected Producer(Buffer buffer) {
        super(buffer);
    }

    @Override
    protected void act() {
        getCurrentProduct().produce();
        ThreadSleep.randomSleep(0.1, 1.0);
    }
}
