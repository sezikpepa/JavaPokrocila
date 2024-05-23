package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.scene.control.Button;

/**
 * Basic button of javafx, but with already set styles
 */
public class BasicBlueButton extends Button {

    /**
     * Creates new blue button
     * @param text Text of the button
     */
    public BasicBlueButton(String text){
        super(text);
        setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-cursor: hand;");
    }


}
