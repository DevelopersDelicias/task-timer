package com.developersdelicias.tasktimer.model;

import java.util.Timer;
import java.util.TimerTask;

import static com.developersdelicias.tasktimer.model.TaskTimer.State.PAUSED;
import static com.developersdelicias.tasktimer.model.TaskTimer.State.RUNNING;
import static com.developersdelicias.tasktimer.model.TaskTimer.State.STOPPED;

public class TaskTimer {

    private final TaskTimerView view;
    private State actualState = STOPPED;
    private Timer timer;
    private long elapsedTime = 0L;
    private long initialTime;

    public TaskTimer(TaskTimerView view) {
        this.view = view;
        this.view.initialState();
    }

    public void start() {
        if (actualState == RUNNING)
            throw new TaskTimerAlreadyStartedException();

        actualState = RUNNING;
        initialTime = System.currentTimeMillis();
        view.startState();
        updateView();
    }

    private void updateView() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - initialTime;
                view.updateTime(elapsedTime);
            }
        }, 1000, 1000);
    }

    void pause() {
        if (actualState == PAUSED)
            throw new TaskTimerAlreadyPausedException();

        actualState = PAUSED;
        timer.cancel();
        view.onPause();
    }

    void play() {
        if (!isPaused())
            throw new TaskTimerCannotPlayException();

        actualState = RUNNING;
        view.onPlay();
        initialTime = System.currentTimeMillis() - elapsedTime;
        updateView();
    }

    public void stop() {
        if (actualState == STOPPED)
            throw new TaskTimerAlreadyStoppedException();

        if (view.shouldStop()) {
            actualState = STOPPED;
            view.initialState();
            timer.cancel();
        }
    }

    boolean isRunning() {
        return actualState == RUNNING;
    }

    boolean isPaused() {
        return actualState == PAUSED;
    }

    public void togglePauseAndPlay() {
        if (isPaused()) {
            play();
        } else {
            pause();
        }
    }

    enum State {
        STOPPED, PAUSED, RUNNING
    }
}
