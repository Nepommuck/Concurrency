package lab5.task1;

import lab5.util.Tuple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Array2d<T> {
    private final int rows;
    private final int columns;
    private final List<List<T>> data;

    public Array2d(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        data = IntStream.range(0, rows)
          .boxed()
          .map(rowIndex ->
            IntStream.range(0, columns)
              .boxed()
              .map(i -> (T) null)
              .collect(Collectors.toList())
          )
          .toList();
    }

    public void set(Tuple<Integer, Integer> position, T value) {
        set(position.x, position.y, value);
    }
    public void set(int x, int y, T value) {
        data.get(y).set(x, value);
    }

    public Optional<T> get(int x, int y) {
        if (x < 0 || x >= columns || y < 0 || y >= rows)
            throw new IllegalArgumentException("Index (" + x + ", " + y +
              ") is out of range of array of size (" + columns + ", " + rows + ")");
        return Optional.ofNullable(data.get(y).get(x));
    }

    public T getUnsafe(int x, int y) {
        final var result = get(x, y);
        if (result.isEmpty())
            throw new IllegalStateException("Index (" + x + ", " + y + ") is empty");
        return result.get();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var row : data)
            result.append(row.toString()).append("\n");
        return result.toString();
    }
}
