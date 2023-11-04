package common;

public class ThreadRandomSleep {
    private static double secondsToMiliseconds(double seconds) {
        return 1000 * seconds;
    }

    public static void sleep(double minSleepSeconds, double maxSleepSeconds) {
        final long minSleepMilliseconds = (long) (secondsToMiliseconds(minSleepSeconds));
        final long maxSleepMilliseconds = (long) (secondsToMiliseconds(maxSleepSeconds));

        final long sleepTime = minSleepMilliseconds + (long) (Math.random() * (maxSleepMilliseconds - minSleepMilliseconds));

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
