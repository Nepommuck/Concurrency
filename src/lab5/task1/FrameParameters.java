package lab5.task1;

import lab5.util.Tuple;

import java.util.List;
import java.util.stream.IntStream;

public class FrameParameters {
    public final double zoom;
    public final int height;
    public final int width;
    public final int margin;

    public FrameParameters(int height, int width, double zoom) {
        this(zoom, height, width, 100);
    }
    public FrameParameters(double zoom, int height, int width, int margin) {
        this.zoom = zoom;
        this.height = height;
        this.width = width;
        this.margin = margin;
    }

    public List<Tuple<Integer, Integer>> getAllPoints() {
        return IntStream.range(0, height)
          .boxed()
          .flatMap(y ->
            IntStream.range(0, width)
              .boxed()
              .map(x -> new Tuple<>(x, y))
          )
          .toList();
    }

    public int getNumberOfPixels() {
        return width * height;
    }
}
