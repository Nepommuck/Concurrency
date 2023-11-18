package lab5.task1;

import lab5.util.Tuple;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static lab5.util.ListUtil.splitList;

public class MandelbrotCalculator {
    private final FrameParameters frameParameters;
    private final int maxNumberOfIterations;

    public MandelbrotCalculator(FrameParameters frameParameters, int maxNumberOfIterations) {
        this.frameParameters = frameParameters;
        this.maxNumberOfIterations = maxNumberOfIterations;
    }

    /**
     * @return Tuple of `Array2d` containing calculated values and time in milliseconds
     */
    public Tuple<Array2d<Integer>, Double> calculate(int numberOfThreads, int numberOfTasks) {
        final Array2d<Integer> result = new Array2d<>(frameParameters.height, frameParameters.width);

        final var pointBatches = splitList(frameParameters.getAllPoints(), numberOfTasks);

        final long startTime = System.nanoTime();
        ExecutorService threadPool = newFixedThreadPool(numberOfThreads);

        final var futures = pointBatches
          .stream()
          .map(points -> new MandelbrotCalculateTask(
            points,
            x -> (x - frameParameters.width / 2d) / frameParameters.zoom,
            y -> (y - frameParameters.height / 2d) / frameParameters.zoom,
            maxNumberOfIterations
          ))
          .map(threadPool::submit)
          .toList();

        threadPool.shutdown();

        futures.stream()
          .map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Exception during future value retrieval: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
          )
          .forEach(map -> {
                for (var position : map.keySet())
                    result.set(position, map.get(position));
            }
          );
        final double timeElapsedMs = (System.nanoTime() - startTime) / 1_000_000d;
        return new Tuple<>(result, timeElapsedMs);
    }
}
