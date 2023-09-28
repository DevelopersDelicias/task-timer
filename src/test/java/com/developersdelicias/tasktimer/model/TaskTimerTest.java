package com.developersdelicias.tasktimer.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.developersdelicias.tasktimer.model.exception.AlreadyPausedException;
import com.developersdelicias.tasktimer.model.exception.AlreadyStartedException;
import com.developersdelicias.tasktimer.model.exception.AlreadyStoppedException;
import com.developersdelicias.tasktimer.model.exception.CannotPlayException;
import org.junit.Before;
import org.junit.Test;

public class TaskTimerTest {

    private TaskTimer taskTimer;

    private TaskTimerView view;

    @Before
    public void setUp() {
        view = mock(TaskTimerView.class);
        taskTimer = new TaskTimer(view);
    }

    @Test
    public void should_be_stopped_by_default() {
        assertFalse(taskTimer.isRunning());
    }

    @Test
    public void should_inform_viewer_to_prepare_initial_states() {
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

    @Test(expected = AlreadyStartedException.class)
    public void should_throw_illegal_operation_exception_when_starts_two_times() {
        taskTimer.start();
        taskTimer.start();
    }

    @Test(expected = AlreadyStoppedException.class)
    public void should_throw_already_stopped_exception() {
        taskTimer.stop();
    }

    @Test
    public void should_stop_a_timer() {
        when(view.shouldStop()).thenReturn(true);
        taskTimer.start();
        taskTimer.stop();
        assertFalse(taskTimer.isRunning());
        verify(view, times(2)).initialState();
    }

    @Test
    public void should_pause_a_timer() {
        taskTimer.start();
        taskTimer.togglePauseAndPlay();
        assertFalse(taskTimer.isRunning());
        assertTrue(taskTimer.isPaused());
    }

    @Test(expected = AlreadyPausedException.class)
    public void should_throw_already_paused_exception() {
        taskTimer.start();
        taskTimer.togglePauseAndPlay();
        taskTimer.pause();
    }

    @Test
    public void should_be_not_paused_by_default() {
        assertFalse(taskTimer.isPaused());
    }

    @Test
    public void should_play_a_paused_timer() {
        taskTimer.start();
        taskTimer.togglePauseAndPlay();
        taskTimer.togglePauseAndPlay();
        assertFalse(taskTimer.isPaused());
        assertTrue(taskTimer.isRunning());
        verify(view).onPause();
        verify(view).onPlay();
    }

    @Test(expected = CannotPlayException.class)
    public void should_throw_cannot_play_exception() {
        taskTimer.start();
        taskTimer.play();
    }

    @Test(expected = CannotPlayException.class)
    public void should_throw_cannot_play_exception_by_default() {
        taskTimer.play();
    }
}
