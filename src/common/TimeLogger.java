package common;

import java.time.LocalTime;

public class TimeLogger {
    public static void log(final String message) {
        final String timeStr = LocalTime.now().toString().substring(0, 11);
        System.out.println(timeStr + " | " + message);
    }
}
