package com.developersdelicias.tasktimer.ui;

import com.developersdelicias.tasktimer.format.BasicTimeFormat;
import com.developersdelicias.tasktimer.format.TimeFormat;
import com.developersdelicias.tasktimer.model.TaskTimer;
import com.developersdelicias.tasktimer.model.TaskTimerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

public class TaskTimerScreen extends JFrame implements TaskTimerView {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TaskTimer taskTimer;
    private JPanel mainPanel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JLabel timeLabel;
    private final TimeFormat timeFormat;

    public TaskTimerScreen() throws HeadlessException {
        super("Task Timer Application");
        lookAndFeel();
        setSize(600, 600);
        setResizable(false);
        init();
        pack();
        timeFormat = new BasicTimeFormat();
        this.taskTimer = new TaskTimer(this);
    }

    private void init() {
        createComponents();
        addComponents();
    }

    private void createComponents() {
        createMainPanel();
        createButtons();
        createTimeLabel();
    }

    private void addComponents() {
        mainPanel.add(startButton);
        mainPanel.add(pauseButton);
        mainPanel.add(stopButton);
        mainPanel.add(timeLabel);
        getContentPane().add(mainPanel);
    }

    private void createTimeLabel() {
        timeLabel = new JLabel("00:00:00");
        timeLabel.setName("timeLabel");
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setName("mainPanel");
    }

    private void createButtons() {
        startButton = new JButton("Start");
        startButton.setName("startButton");
        startButton.addActionListener(new StartActionListener());

        pauseButton = new JButton("Pause");
        pauseButton.setName("pauseButton");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new PauseActionListener());

        stopButton = new JButton("Stop");
        stopButton.setName("stopButton");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new StopActionListener());
    }

    private void lookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            logger.warn("Could not apply look and feel style. Native Style will be used", ex);
        }
    }

    @Override
    public void initialState() {
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText("Pause");
        stopButton.setEnabled(false);
        timeLabel.setText(format(0L));
    }

    @Override
    public void startState() {
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    @Override
    public boolean shouldStop() {
        int option = showConfirmDialog(TaskTimerScreen.this, "Are you sure to stop the timer?", "Task Timer", YES_NO_OPTION);
        return option == YES_OPTION;
    }

    @Override
    public void onPause() {
        pauseButton.setText("Play");
    }

    @Override
    public void onPlay() {
        pauseButton.setText("Pause");
    }

    @Override
    public void updateTime(long elapsedTime) {
        timeLabel.setText(format(elapsedTime));
    }

    private String format(final long timeElapsed) {
        return timeFormat.format(timeElapsed);
    }

    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            taskTimer.start();
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            taskTimer.togglePauseAndPlay();
        }
    }

    private class StopActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            taskTimer.stop();
        }
    }
}
