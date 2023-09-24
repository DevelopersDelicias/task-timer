package com.developersdelicias.tasktimer.ui;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.timing.Timeout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.swing.JButton;
import java.awt.Dialog;
import java.awt.Frame;
import java.util.List;

import static org.assertj.swing.timing.Timeout.timeout;

public class TaskTimerScreenTest {

    private static final String INITIAL_TIME = "00:00:00";
    private static final int ONE_SECOND = 1000;
    private static final int TWO_SECONDS = 2000;
    private static final int THREE_SECONDS = 3000;
    private FrameFixture window;

    @Before
    public void setUp() {
        window = new FrameFixture(createFrame());
        window.show();
    }

    @Test
    public void should_show_a_button_to_start_the_timer() {
        startButton().requireText("Start").requireEnabled();
    }

    @Test
    public void should_show_a_button_to_pause_the_timer() {
        pauseButton().requireText("Pause").requireDisabled();
    }

    @Test
    public void should_show_a_button_to_stop_the_timer() {
        stopButton().requireText("Stop").requireDisabled();
    }

    @Test
    public void should_disable_start_button_when_is_clicked() {
        startAnyTask();
        startButton().requireDisabled();
    }

    @Test
    public void should_enable_pause_button_when_click_on_start_button() {
        startAnyTask();
        pauseButton().requireEnabled();
    }

    @Test
    public void should_enable_stop_button_when_click_on_start_button() {
        startAnyTask();
        stopButton().requireEnabled();
    }

    @Test
    public void should_update_second_by_second_the_timer_label() throws Exception {
        startAnyTask();
        sleepFor(ONE_SECOND);
        verifyTimeElapsedIs("00:00:01");
        sleepFor(ONE_SECOND);
        verifyTimeElapsedIs("00:00:02");
        sleepFor(ONE_SECOND);
        verifyTimeElapsedIs("00:00:03");
    }

    @Test
    public void should_change_text_to_play_when_pause_is_clicked() {
        startAnyTask();
        clickOnPauseButton();
        pauseButton().requireText("Play");
    }

    @Test
    public void should_pause_and_play() throws Exception {
        startAnyTask();
        sleepFor(THREE_SECONDS);
        clickOnPauseButton();
        verifyTimeElapsedIs("00:00:03");
        sleepFor(TWO_SECONDS);
        clickOnPauseButton();
        sleepFor(TWO_SECONDS);
        verifyTimeElapsedIs("00:00:05");
    }

    @Test
    public void should_change_text_to_pause_when_play_is_clicked() {
        startAnyTask();
        clickOnPauseButton();
        clickOnPauseButton();
        pauseButton().requireText("Pause");
    }

    @Test
    public void should_show_a_confirmation_message_when_stop_is_clicked() {
        startAnyTask();
        clickOnStopButton();
        optionPane().requireMessage("Are you sure to stop the timer?").requireQuestionMessage();
    }

    @Test
    public void should_reset_screen_when_user_stops_the_timer() throws Exception {
        startAnyTask();
        sleepFor(TWO_SECONDS);
        stopTask();
        startButton().requireEnabled();
        pauseButton().requireDisabled().requireText("Pause");
        stopButton().requireDisabled();
        verifyTimeElapsedIs(INITIAL_TIME);
        sleepFor(TWO_SECONDS);
        verifyTimeElapsedIs(INITIAL_TIME);
    }

    @Test
    public void should_have_a_label_to_display_the_time() {
        verifyTimeElapsedIs(INITIAL_TIME);
    }

    @Test
    public void should_prompt_an_input_message_to_capture_task_description() {
        clickOnStartButton();

        DialogFixture dialog = window.dialog(new GenericTypeMatcher<Dialog>(Dialog.class) {
            @Override
            protected boolean isMatching(Dialog dialog) {
                return "Create Task".equals(dialog.getTitle()) && dialog.isVisible()
                        ;
            }
        }, Timeout.timeout(1000));
        dialog.textBox().enterText("Task #1");
        dialog.label("OptionPane.label").requireText("Enter task description");
        clickOnOkButton(dialog);

        windowTitleShouldBe("Task Timer Application - Task #1");
    }

    @Test
    public void should_not_append_multiple_times_the_task_description_to_title() {
        startTaskWithDescription("Task #1");
        windowTitleShouldBe("Task Timer Application - Task #1");
        stopTask();
        startTaskWithDescription("Task #2");
        windowTitleShouldBe("Task Timer Application - Task #2");
    }

    @Test
    public void should_clear_title_when_timer_is_stopped() {
        startAnyTask();
        stopTask();
        windowTitleShouldBe("Task Timer Application");
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    private void windowTitleShouldBe(String windowTitle) {
        window.requireTitle(windowTitle);
    }

    private void startTaskWithDescription(String taskDescription) {
        clickOnStartButton();
        DialogFixture dialog = window.dialog(Timeout.timeout(1000));
        dialog.textBox().setText(taskDescription);
        clickOnOkButton(dialog);
    }

    private void clickOnOkButton(DialogFixture dialog) {
        dialog.button(
                new GenericTypeMatcher<JButton>(JButton.class) {
                    @Override
                    protected boolean isMatching(JButton button) {
                        return List.of("OK", "Aceptar").contains(button.getText());
                    }
                }
        ).click();
    }

    private void stopTask() {
        clickOnStopButton();
        optionPane().yesButton().click();
    }

    private void startAnyTask() {
        startTaskWithDescription("A Task");
    }

    private JOptionPaneFixture optionPane() {
        return window.optionPane(timeout(ONE_SECOND));
    }

    private void clickOnStopButton() {
        stopButton().click();
    }

    private void clickOnPauseButton() {
        pauseButton().click();
    }

    private void clickOnStartButton() {
        startButton().click();
    }

    private void verifyTimeElapsedIs(String expected) {
        window.label("timeLabel").requireText(expected);
    }

    private void sleepFor(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    private JButtonFixture stopButton() {
        return button("stopButton");
    }

    private JButtonFixture pauseButton() {
        return button("pauseButton");
    }

    private JButtonFixture startButton() {
        return button("startButton");
    }

    private JButtonFixture button(String name) {
        return window.button(name);
    }

    private Frame createFrame() {
        return GuiActionRunner.execute(TaskTimerScreen::new);
    }
}
