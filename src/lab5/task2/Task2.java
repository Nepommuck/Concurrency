package lab5.task2;

import lab5.task1.FrameParameters;
import lab5.task1.MandelbrotCalculator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Task2 {
    public static void main(String[] args) {
        final int MAX_NUMBER_OF_ITERATIONS = 570;
        final var FRAME_PARAMETERS = new FrameParameters(800, 1500, 400);

        final var PROCESSOR_THREADS = 12;
        final var THREADS_CONFIGURATIONS = List.of(1, PROCESSOR_THREADS, 2 * PROCESSOR_THREADS);
        final List<Function<Integer, Integer>> TASKS_CONFIGURATIONS = List.of(
          numberOfThreads -> numberOfThreads,
          numberOfThreads -> 10 * numberOfThreads,
          numberOfThreads -> FRAME_PARAMETERS.getNumberOfPixels()
        );

        final var CALCULATIONS_PER_CONFIGURATION = 10;

        final var mandelbrotCalculator = new MandelbrotCalculator(FRAME_PARAMETERS, MAX_NUMBER_OF_ITERATIONS);
        final var persistenceManager = new PersistenceManager();

        for (var numberOfThreads : THREADS_CONFIGURATIONS)
            for (var numberOfTasksFunction : TASKS_CONFIGURATIONS) {
                final var numberOfTasks = numberOfTasksFunction.apply(numberOfThreads);

                final var results = IntStream.range(0, CALCULATIONS_PER_CONFIGURATION)
                  .boxed()
                  .map(i -> mandelbrotCalculator.calculate(numberOfThreads, numberOfTasks))
                  .map(calculationResult -> calculationResult.y)
                  .sorted()
                  .toList();

                System.out.println(
                  "Number of threads: " + numberOfThreads + "\n" +
                    "Number of tasks: " + numberOfTasks + "\n" +
                    "Calculation time: " + results + " ms\n"
                );
                persistenceManager.addRecord(numberOfThreads, numberOfTasks, results);
            }

        try {
            persistenceManager.exportToFile(
              Paths.get("src", "lab5", "task2", "exports", "data.json")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
