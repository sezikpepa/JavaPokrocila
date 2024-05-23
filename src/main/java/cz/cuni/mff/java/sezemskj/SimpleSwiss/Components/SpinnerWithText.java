package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Shows text as the label - but in specific interval adds dots to the text - loading effect
 */
public class SpinnerWithText extends Label {

    /**
     * How many commas should be shown in the label after the text
     */
    private int _commas = 0;

    /**
     * Text of the label without commas
     */
    private final String _defaultText;

    /**
     * Creates new loader
     * @param text Text which will be shown on the label
     */
    public SpinnerWithText(String text) {
        _defaultText = text;

        Timer timer = new Timer();
        TimerTask taskToChangeDots = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateText());
            }
        };

        timer.scheduleAtFixedRate(taskToChangeDots, 0, 300);

    }

    /**
     * After elapsed interval it updates number of dots
     */
    private void updateText() {
        _commas++;
        _commas = _commas % 5;
        setText(_defaultText + " " + ".".repeat(_commas));
    }
}
