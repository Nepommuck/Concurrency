package lab4.task1;

import common.ThreadSleep;

public class Processor extends Actor {
    private final int processStep;

    protected Processor(Buffer buffer, int processStep) {
        super(buffer);
        this.processStep = processStep;
    }

    @Override
    protected void act() {
        getCurrentProduct().process(processStep);
        ThreadSleep.randomSleep((processStep + 1) * 0.4, (processStep + 1) * 1.0);
    }
}
