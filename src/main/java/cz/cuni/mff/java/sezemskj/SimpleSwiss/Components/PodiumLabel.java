package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Big numbers on podium - 1,2,3. They are invisible but are slowly shown.
 */
public class PodiumLabel extends Label {

    /**
     * Animation of fade of the label
     */
    private final FadeTransition fadeTransition;

    /**
     * Creates new label
     * @param text Text of the label
     * @param x X coordinate
     * @param y Y coordinate
     * @param font Font of the text
     * @param durationInSeconds How many seconds should animation last
     */
    public PodiumLabel(String text, int x, int y, Font font, int durationInSeconds){
        super(text);

        setFont(font);
        setLayoutX(x);
        setLayoutY(y);
        setOpacity(0);

        fadeTransition = new FadeTransition();
        fadeTransition.setDuration(new Duration(durationInSeconds * 1000));
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
    }

    /**
     * Starts the animation
     */
    public void play(){
        fadeTransition.play();
    }


}
