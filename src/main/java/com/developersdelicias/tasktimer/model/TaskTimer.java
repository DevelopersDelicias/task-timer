package com.developersdelicias.tasktimer.model;

import static com.developersdelicias.tasktimer.model.TaskTimer.State.PAUSED;
import static com.developersdelicias.tasktimer.model.TaskTimer.State.RUNNING;
import static com.developersdelicias.tasktimer.model.TaskTimer.State.STOPPED;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will handle the different task timer states.
 */
public class TaskTimer {
    /**
     * A reference to TaskTimerView model object.
     */
    private final TaskTimerView view;
    /**
     * The actual state.
     */
    private State actualState = STOPPED;
    /**
     * Timer reference.
     */
    private Timer timer;
    /**
     * Elapsed time.
     */
    private long elapsedTime = 0L;
    /**
     * Initial time.
     */
    private long initialTime;

    /**
     * Timer Period.
     */
    private static final int TIMER_PERIOD = 1000;
    /**
     * Timer Delay.
     */
    private static final int TIMER_DELAY = 1000;

    /**
     * Timer reference.
     *
     * @param view
     *            Is an implementation of the Task Timer View model object.
     */
    public TaskTimer(final TaskTimerView view) {
        this.view = view;
        this.view.initialState();
    }

    /**
     * Start timer implementation.
     */
    public final void start() {
        if (actualState == RUNNING) {
            throw new TaskTimerAlreadyStartedException();
        }
        actualState = RUNNING;
        initialTime = System.currentTimeMillis();
        view.startState();
        updateView();
    }

    /**
     * Update view implementation.
     */
    private void updateView() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - initialTime;
                view.updateTime(elapsedTime);
            }
        }, TIMER_DELAY, TIMER_PERIOD);
    }

    /**
     * Pause timer implementation.
     */
    final void pause() {
        if (actualState == PAUSED) {
            throw new TaskTimerAlreadyPausedException();
        }
        actualState = PAUSED;
        timer.cancel();
        view.onPause();
    }

    /**
     * Play timer implementation.
     */
    final void play() {
        if (!isPaused()) {
            throw new TaskTimerCannotPlayException();
        }
        actualState = RUNNING;
        view.onPlay();
        initialTime = System.currentTimeMillis() - elapsedTime;
        updateView();
    }

    /**
     * Stop timer implementation.
     */
    public final void stop() {
        if (actualState == STOPPED) {
            throw new TaskTimerAlreadyStoppedException();
        }
        if (view.shouldStop()) {
            actualState = STOPPED;
            view.initialState();
            timer.cancel();
        }
    }

    /**
     * Change the actual time state to paused.
     *
     * @return the actualState as PAUSED.
     */
    final boolean isRunning() {
        return actualState == RUNNING;
    }

    /**
     * Change the actual time state to paused.
     *
     * @return the actualState as PAUSED.
     */
    final boolean isPaused() {

        return actualState == PAUSED;
    }

    /**
     * Toggle between pause and play.
     */
    public final void togglePauseAndPlay() {
        if (isPaused()) {
            play();
        } else {
            pause();
        }
    }

    /**
     * Timer states.
     */
    enum State {
        /**
         * Timer stopped state.
         */
        STOPPED,
        /**
         * Timer paused state.
         */
        PAUSED,
        /**
         * Timer running state.
         */
        RUNNING,
    }
}
