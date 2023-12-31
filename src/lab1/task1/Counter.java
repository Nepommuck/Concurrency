package lab1.task1;

public abstract class Counter {
    protected int value = 0;

    public abstract void increment();

    public abstract void decrement();

    public int getValue() {
        return value;
    }
}
