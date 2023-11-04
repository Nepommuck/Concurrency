package lab4.task1;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Product {
    private int state = -1;
    private final int stepsToProcess;
    private final Lock lock = new ReentrantLock();
    private final Condition produceCondition = lock.newCondition();
    private final Condition consumeCondition = lock.newCondition();
    private final List<Condition> processConditions;

    public Product(int stepsToProcess) {
        this.stepsToProcess = stepsToProcess;
        this.processConditions = IntStream.range(0, stepsToProcess)
          .boxed()
          .map(i -> lock.newCondition())
          .toList();
    }

    @Override
    public String toString() {
        // Not produced
        if (state < 0)
            return "-";

        // Just produced
        if (state == 0)
            return "P";

        // Fully processed
        if (state == stepsToProcess)
            return "R";

        // Currently processing
        return String.valueOf(state);
    }

    public void produce() {
        modifyProduct(
          state -> 0,
          state -> state >= 0,
          produceCondition,
          processConditions.get(0)
        );
    }

    public void process(int stepNumber) {
        modifyProduct(
          state -> state + 1,
          state -> state != stepNumber,
          processConditions.get(stepNumber),
          (stepNumber < stepsToProcess - 1) ? processConditions.get(stepNumber + 1) : consumeCondition
        );
    }

    public void consume() {
        modifyProduct(
          state -> -1,
          state -> state < stepsToProcess,
          consumeCondition,
          produceCondition
        );
    }

    private void modifyProduct(
      Function<Integer, Integer> stateAfterSuccess,
      Function<Integer, Boolean> waitPredicate,
      Condition awaitCondition,
      Condition signalCondition
    ) {
        lock.lock();
        try {
            while (waitPredicate.apply(state))
                awaitCondition.await();

            state = stateAfterSuccess.apply(state);
            signalCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
