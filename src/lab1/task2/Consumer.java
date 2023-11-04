package lab1.task2;

import java.util.Optional;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int numberOfOperations;
    private final String name;

    public Consumer(Buffer buffer, int numberOfOperations) {
        this(buffer, numberOfOperations, Optional.empty());
    }

    public Consumer(Buffer buffer, int numberOfOperations, Optional<String> name) {
        this.buffer = buffer;
        this.numberOfOperations = numberOfOperations;
        this.name = name.orElse("CONSUMER");
    }

    public void run() {
        for (int i = 0; i < numberOfOperations; i++) {
            String message = buffer.take();
            System.out.println(name + ": Taken message \"" + message + "\"");
        }
    }
}
