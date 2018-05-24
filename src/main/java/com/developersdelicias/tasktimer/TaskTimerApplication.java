package com.developersdelicias.tasktimer;

import com.developersdelicias.tasktimer.ui.TaskTimerScreen;
import javax.swing.WindowConstants;

@SuppressWarnings("WeakerAccess")
public class TaskTimerApplication {

    public static void main(String[] args) {
        TaskTimerScreen app = new TaskTimerScreen();
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
