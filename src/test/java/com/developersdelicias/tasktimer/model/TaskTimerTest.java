package com.developersdelicias.tasktimer.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TaskTimerTest {

    private TaskTimer taskTimer;

    private TaskTimerView view;

    @Before
    public void setUp() throws Exception {
        view = mock(TaskTimerView.class);
        taskTimer = new TaskTimer(view);
    }

    @Test
    public void should_be_stopped_by_default() throws Exception {
        assertFalse(taskTimer.isRunning());
    }

    @Test
    public void should_inform_viewer_to_prepare_initial_states() throws Exception {
        verify(view).initialState();
    }

    @Test
    public void should_start_running() throws Exception {
        taskTimer.start();
        assertTrue(taskTimer.isRunning());
        verify(view).startState();
        Thread.sleep(3000);
        verify(view, times(3)).updateTime(anyLong());
    }

    @Test(expected = TaskTimerAlreadyStartedException.class)
    public void should_throw_illegal_operation_exception_when_starts_two_times() throws Exception {
        taskTimer.start();
        taskTimer.start();
    }

    @Test(expected = TaskTimerAlreadyStoppedException.class)
    public void should_throw_already_stopped_exception() throws Exception {
        taskTimer.stop();
    }

    @Test
    public void should_stop_a_timer() throws Exception {
        when(view.shouldStop()).thenReturn(true);
        taskTimer.start();
        taskTimer.stop();
        assertFalse(taskTimer.isRunning());
        verify(view, times(2)).initialState();
    }

    @Test
    public void should_pause_a_timer() throws Exception {
        taskTimer.start();
        taskTimer.togglePauseAndPlay();
        assertFalse(taskTimer.isRunning());
        assertTrue(taskTimer.isPaused());
    }

    @Test(expected = TaskTimerAlreadyPausedException.class)
    public void should_throw_already_paused_exception() throws Exception {
        taskTimer.start();
        taskTimer.togglePauseAndPlay();
        taskTimer.pause();
    }

    @Test
    public void should_be_not_paused_by_default() throws Exception {
        assertFalse(taskTimer.isPaused());
    }

    @Test
    public void should_play_a_paused_timer() throws Exception {
        taskTimer.start();
        taskTimer.togglePauseAndPlay();
        taskTimer.togglePauseAndPlay();
        assertFalse(taskTimer.isPaused());
        assertTrue(taskTimer.isRunning());
        verify(view).onPause();
        verify(view).onPlay();
    }

    @Test(expected = TaskTimerCannotPlayException.class)
    public void should_throw_cannot_play_exception() throws Exception {
        taskTimer.start();
        taskTimer.play();
    }

    @Test(expected = TaskTimerCannotPlayException.class)
    public void should_throw_cannot_play_exception_by_default() throws Exception {
        taskTimer.play();
    }
}