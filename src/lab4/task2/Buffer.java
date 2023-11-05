package lab4.task2;

import java.util.Set;
import java.util.concurrent.Semaphore;

public class Buffer {
    protected final int bufferSize;
    protected final CustomStack<Element> elements = new CustomStack<>();
    private final Semaphore canConsume;
    private final Semaphore canProduce;

    public Buffer(int size) {
        this.bufferSize = size;

        this.canProduce = new Semaphore(bufferSize);
        this.canConsume = new Semaphore(0);
    }

    protected int currentNumberOfElements() {
        return elements.size();
    }

    protected int numberOfFreeSpots() {
        return bufferSize - currentNumberOfElements();
    }

    public void put(Set<Element> newElements) {
        try {
            canProduce.acquire(newElements.size());
            elements.addAll(newElements);
            canConsume.release(newElements.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Set<Element> get(int numberOfElements) {
        try {
            canConsume.acquire(numberOfElements);
            final var result = elements.pop(numberOfElements);
            canProduce.release(numberOfElements);

            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
