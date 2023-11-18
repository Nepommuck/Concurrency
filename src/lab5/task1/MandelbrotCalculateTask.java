package lab5.task1;

import lab5.util.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class MandelbrotCalculateTask implements Callable<Map<Tuple<Integer, Integer>, Integer>> {
    private final List<Tuple<Integer, Integer>> points;
    private final Function<Integer, Double> mapX;
    private final Function<Integer, Double> mapY;
    private final int maxIter;

    public MandelbrotCalculateTask(List<Tuple<Integer, Integer>> points, Function<Integer, Double> mapX, Function<Integer, Double> mapY, int maxIter) {
        this.points = points;
        this.mapX = mapX;
        this.mapY = mapY;
        this.maxIter = maxIter;
    }

    private Map<Tuple<Integer, Integer>, Integer> calculate() {
        double zx, zy, cX, cY, tmp;
        Map<Tuple<Integer, Integer>, Integer> result = new HashMap<>();

        for (var point : points) {
            zx = zy = 0;
            cX = mapX.apply(point.x);
            cY = mapY.apply(point.y);
            int iter = maxIter;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            result.put(point, iter | (iter << 8));
        }
        return result;
    }

    @Override
    public Map<Tuple<Integer, Integer>, Integer> call() {
        return calculate();
    }
}
