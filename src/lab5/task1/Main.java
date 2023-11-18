package lab5.task1;

import common.TimeLogger;

public class Main {
    public static void main(String[] args) {
        final int MAX_NUMBER_OF_ITERATIONS = 570;
        final int NUMBER_OF_THREADS = 8;
        final int NUMBER_OF_TASKS = 50;
        final var FRAME_PARAMETERS = new FrameParameters(800, 1500, 400);

        final var calculation = new MandelbrotCalculator(
          FRAME_PARAMETERS, MAX_NUMBER_OF_ITERATIONS
        ).calculate(NUMBER_OF_THREADS, NUMBER_OF_TASKS);

        final var calculationResult = calculation.x;
        final double calculationTimeMs = calculation.y;
        TimeLogger.log("Calculation time: " + calculationTimeMs + "ms");

        new MandelbrotFrame(calculationResult, FRAME_PARAMETERS).setVisible(true);
    }
}
