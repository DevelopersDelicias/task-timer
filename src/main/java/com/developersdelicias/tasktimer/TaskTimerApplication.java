package com.developersdelicias.tasktimer;

import com.developersdelicias.tasktimer.ui.TaskTimerScreen;
import javax.swing.WindowConstants;

/**
 * This class will be the implementation of the application.
 */
@SuppressWarnings("WeakerAccess")
public final class TaskTimerApplication {

    /**
     * Can't be instantiated directly.
     */
    private TaskTimerApplication() {
        // empty constructor
    }

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
