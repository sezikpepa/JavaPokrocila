package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Javafx line with implemented animation - from zero size to maximum
 */
public class GrowingLine extends Line {

    /**
     * Keyframes of the line during time
     */
    private final Timeline _timeline;

    /**
     * Creates new growing line
     * @param startX X coordinate of the line - start
     * @param startY Y coordinate of the line - start
     * @param endX X coordinate of the line - end
     * @param endY Y coordinate of the line - end
     * @param durationSeconds How many seconds the line should be animated
     */
    public GrowingLine(double startX, double startY, double endX, double endY, double durationSeconds) {
        super(startX, startY, startX, startY);

        setStroke(Color.BLACK);
        setStrokeWidth(2);

        _timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(durationSeconds),
                            new KeyValue(endXProperty(), endX),
                            new KeyValue(endYProperty(), endY));

        _timeline.getKeyFrames().add(keyFrame);
    }

    /**
     * Starts the animation
     */
    public void play() {
        _timeline.play();
    }
}

