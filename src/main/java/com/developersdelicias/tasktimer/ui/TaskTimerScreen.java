package com.developersdelicias.tasktimer.ui;

import com.developersdelicias.tasktimer.format.BasicTimeFormat;
import com.developersdelicias.tasktimer.format.TimeFormat;
import com.developersdelicias.tasktimer.model.TaskTimer;
import com.developersdelicias.tasktimer.model.TaskTimerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the Main Screen of Task Timer Application.
 */
public class TaskTimerScreen extends JFrame implements TaskTimerView {

    /**
     * The text displayed on Pause Button.
     */
    private static final String PAUSE_BUTTON_TEXT = "Pause";
    /**
     * The Screen width.
     */
    private static final int SCREEN_WIDTH = 600;
    /**
     * The Screen height.
     */
    private static final int SCREEN_HEIGHT = 600;
    /**
     * Title that appears at the top of the Window.
     */
    private static final String WINDOW_TITLE = "Task Timer Application";
    /**
     * The logger.
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * A reference to TaskTimer model object.
     */
    private final transient TaskTimer taskTimer;
    /**
     * Time Format used.
     */
    private final transient TimeFormat timeFormat;
    /**
     * The main panel that contains all elements.
     */
    private JPanel mainPanel;
    /**
     * Start button.
     */
    private JButton startButton;
    /**
     * Pause button.
     */
    private JButton pauseButton;
    /**
     * Stop button.
     */
    private JButton stopButton;
    /**
     * This label display the current time.
     */
    private JLabel timeLabel;

    /**
     * Constructor.
     */
    public TaskTimerScreen() {
        super(WINDOW_TITLE);
        lookAndFeel();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        init();
        pack();
        timeFormat = new BasicTimeFormat();
        this.taskTimer = new TaskTimer(this);
    }

    /**
     * Initializes all UI components.
     */
    private void init() {
        createComponents();
        addComponents();
    }

    /**
     * Creates all components.
     */
    private void createComponents() {
        createMainPanel();
        createButtons();
        createTimeLabel();
    }

    /**
     * Adds components to screen.
     */
    private void addComponents() {
        mainPanel.add(startButton);
        mainPanel.add(pauseButton);
        mainPanel.add(stopButton);
        mainPanel.add(timeLabel);
        getContentPane().add(mainPanel);
    }

    /**
     * Creates time label with default display.
     */
    private void createTimeLabel() {
        timeLabel = new JLabel("00:00:00");
        timeLabel.setName("timeLabel");
    }

    /**
     * Creates main panel.
     */
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setName("mainPanel");
    }

    /**
     * Creates all buttons.
     */
    private void createButtons() {
        startButton = new JButton("Start");
        startButton.setName("startButton");
        startButton.addActionListener(new StartActionListener());

        pauseButton = new JButton(PAUSE_BUTTON_TEXT);
        pauseButton.setName("pauseButton");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new PauseActionListener());

        stopButton = new JButton("Stop");
        stopButton.setName("stopButton");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new StopActionListener());
    }

    /**
     * Applies look and feel.
     */
    private void lookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info
                    : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            final String msg = "Could not apply look and feel style. "
                    + "Native Style will be used";
            logger.warn(msg, ex);
        }
    }

    @Override
    public final void initialState() {
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText(PAUSE_BUTTON_TEXT);
        stopButton.setEnabled(false);
        timeLabel.setText(format(0L));
    }

    @Override
    public final void startState() {
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    @Override
    public final boolean shouldStop() {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure to stop the timer?", "Task Timer",
                JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    @Override
    public final void onPause() {
        pauseButton.setText("Play");
    }

    @Override
    public final void onPlay() {
        pauseButton.setText(PAUSE_BUTTON_TEXT);
    }

    @Override
    public final void updateTime(final long elapsedTime) {
        timeLabel.setText(format(elapsedTime));
    }

    /**
     * Applies format to display.
     *
     * @param timeElapsed The Time elapsed in milliseconds.
     * @return The time with the proper format.
     */
    private String format(final long timeElapsed) {
        return timeFormat.format(timeElapsed);
    }

    /**
     * Internal class.
     */
    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String taskDescription = askTaskDescription();

            setTitle(WINDOW_TITLE + " - " + taskDescription);
            taskTimer.start();
        }
    }

    /**
     * Displays an input dialog to the user to have the task description.
     *
     * @return The input value from the user.
     */
    private String askTaskDescription() {
        return JOptionPane.showInputDialog(null, null,
                "Create Task", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Internal class.
     */
    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            taskTimer.togglePauseAndPlay();
        }
    }

    /**
     * Internal class.
     */
    private class StopActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            taskTimer.stop();
        }
    }
}
