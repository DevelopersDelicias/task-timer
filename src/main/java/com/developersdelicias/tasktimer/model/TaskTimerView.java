package com.developersdelicias.tasktimer.model;

public interface TaskTimerView {

    void initialState();

    void startState();

    boolean shouldStop();

    void onPause();

    void onPlay();

    void updateTime(long elapsedTime);
}
