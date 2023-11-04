package common;

public class ThreadSleep {
    private static double secondsToMilliseconds(double seconds) {
        return 1000 * seconds;
    }

    public static void randomSleep(double minSleepSeconds, double maxSleepSeconds) {
        final long minSleepMilliseconds = (long) (secondsToMilliseconds(minSleepSeconds));
        final long maxSleepMilliseconds = (long) (secondsToMilliseconds(maxSleepSeconds));

        final long sleepTime = minSleepMilliseconds + (long) (Math.random() * (maxSleepMilliseconds - minSleepMilliseconds));

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(double sleepSeconds) {
        try {
            Thread.sleep((long) secondsToMilliseconds(sleepSeconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
