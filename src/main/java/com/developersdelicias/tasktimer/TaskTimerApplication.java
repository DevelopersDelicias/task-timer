package com.developersdelicias.tasktimer;

import javax.swing.WindowConstants;

import com.developersdelicias.tasktimer.ui.TaskTimerScreen;

/**
 * This class will be the implementation of the application.
 */
@SuppressWarnings("WeakerAccess")
public class TaskTimerApplication {

    /**
     * Main method of the application.
     *
     * @param args
     *            Main method arguments
     */
    public static void main(final String[] args) {
        TaskTimerScreen app = new TaskTimerScreen();
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
