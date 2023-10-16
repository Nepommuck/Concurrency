package lab1.task1;

public class UnsynchronizedCounter extends Counter {
    @Override
    public void increment() {
        value++;
    }

    @Override
    public void decrement() {
        value--;
    }
}
