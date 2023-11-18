package lab5.util;

import java.util.Objects;

public class Tuple<T, V> {
    public final T x;
    public final V y;

    public Tuple(T x, V y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        Tuple<?, ?> otherTuple = (Tuple<?, ?>) other;

        return this.x == otherTuple.x && y == otherTuple.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
