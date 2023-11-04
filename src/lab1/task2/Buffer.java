package lab1.task2;

import java.util.Optional;

public class Buffer {
    private Optional<String> memory = Optional.empty();

    public synchronized void put(String message) {
        try {
            while (memory.isPresent())
                this.wait();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        memory = Optional.of(message);
        this.notifyAll();
    }

    public synchronized String take() {
        try {
            while (memory.isEmpty())
                this.wait();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String message = memory.get();
        memory = Optional.empty();
        this.notifyAll();

        return message;
    }
}
