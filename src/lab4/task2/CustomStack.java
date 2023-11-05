package lab4.task2;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class CustomStack<E> extends Stack<E> {
    public Set<E> pop(int numberOfElements) {
        if (numberOfElements <= 0)
            throw new IllegalArgumentException("`numberOfElements` must be a positive number, but was " +
              numberOfElements);
        if (numberOfElements > this.elementCount)
            throw new IllegalArgumentException("`numberOfElements` was greater (" + numberOfElements +
              "), than the current stack size (" + elementCount + ")");

        Set<E> result = new HashSet<>();
        for (int i = 0; i < numberOfElements; i++)
            result.add(this.pop());

        return result;
    }
}
