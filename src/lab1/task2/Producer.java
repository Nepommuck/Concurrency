package lab1.task2;

import java.util.Optional;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final int numberOfOperations;
    private final String name;

    public Producer(Buffer buffer, int numberOfOperations) {
        this(buffer, numberOfOperations, Optional.empty());
    }

    public Producer(Buffer buffer, int numberOfOperations, Optional<String> name) {
        this.buffer = buffer;
        this.numberOfOperations = numberOfOperations;
        this.name = name.orElse("PRODUCER");
    }

    public void run() {
        for (int i = 0; i < numberOfOperations; i++) {
            String newMessage = "Message " + i;
            buffer.put(newMessage);
            System.out.println(name + ": Put message \"" + newMessage + "\"");
        }
    }
}
