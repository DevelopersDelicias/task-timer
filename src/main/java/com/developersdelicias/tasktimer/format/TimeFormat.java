package com.developersdelicias.tasktimer.format;

/**
 * Defines a format for Time elements.
 */
public interface TimeFormat {

    /**
     * Creates a String representation of the current time given the number of
     * milliseconds elapsed.
     *
     * @param milliseconds Number of milliseconds
     * @return A String representation of the time elapsed
     */
    String format(long milliseconds);
}
