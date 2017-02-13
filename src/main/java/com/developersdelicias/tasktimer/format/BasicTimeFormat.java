package com.developersdelicias.tasktimer.format;


import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class BasicTimeFormat implements TimeFormat {
    @Override
    public String format(long milliseconds) {
        final long hr = MILLISECONDS.toHours(milliseconds);
        final long min = MILLISECONDS.toMinutes(subtract(milliseconds, hr));
        final long sec = MILLISECONDS.toSeconds(subtract(milliseconds, hr) - MINUTES.toMillis(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }

    private long subtract(long milliseconds, long hr) {
        return milliseconds - HOURS.toMillis(hr);
    }
}
