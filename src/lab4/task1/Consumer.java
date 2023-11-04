package lab4.task1;

import common.ThreadSleep;

public class Consumer extends Actor {
    protected Consumer(Buffer buffer) {
        super(buffer);
    }

    @Override
    protected void act() {
        getCurrentProduct().consume();
        ThreadSleep.randomSleep(3.0, 5.0);
    }
}
