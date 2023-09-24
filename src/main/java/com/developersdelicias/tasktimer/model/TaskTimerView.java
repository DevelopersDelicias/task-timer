package com.developersdelicias.tasktimer.model;

/**
 * Interface with task timer different states.
 */
public interface TaskTimerView {
    /**
     * Task Timer initial state.
     */
    void initialState();

    /**
     * Task timer start state.
     */
    void startState();

    /**
     * Task timer should stop.
     *
     * @return This boolean will return whether if the timer should stop.
     *
     */
    boolean shouldStop();

    /**
     * Task timer on pause.
     */
    void onPause();

    /**
     * Task timer on play.
     */
    void onPlay();

    /**
     * Task timer update time.
     *
     * @param elapsedTime
     *            Elapsed time to update.
     */
    void updateTime(long elapsedTime);
}
