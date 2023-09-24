package com.developersdelicias.tasktimer.format;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Implements Basic Time Format using hh:mm:ss.
 */
public class BasicTimeFormat implements TimeFormat {

    @Override
    public final String format(final long milliseconds) {
        final long hr = MILLISECONDS.toHours(milliseconds);
        final long min = MILLISECONDS.toMinutes(subtract(milliseconds, hr));
        final long sec = MILLISECONDS.toSeconds(
                subtract(milliseconds, hr) - MINUTES.toMillis(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }

    /**
     * Extracts number of milliseconds given a number of hours.
     *
     * @param milliseconds The total amount of milliseconds.
     * @param hr           The number of hours to extract milliseconds.
     * @return Number of milliseconds without hours.
     */
    private long subtract(final long milliseconds, final long hr) {
        return milliseconds - HOURS.toMillis(hr);
    }
}
