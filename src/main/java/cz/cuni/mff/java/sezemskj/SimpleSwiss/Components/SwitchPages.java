package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Two buttons with arrows - possible to change pages - to go left or right
 */
public class SwitchPages extends HBox {

    private final Button _leftButton;
    private final Button _rightButton;

    public SwitchPages() {
        _leftButton = new Button("<");
        _leftButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _leftButton.setStyle("-fx-cursor: hand;");

        _rightButton = new Button(">");
        _rightButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _rightButton.setStyle("-fx-cursor: hand;");

        this.getChildren().addAll(_leftButton, _rightButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    /**
     * Sets function which should be called after left button click
     * @param functionToBeCalled function which should be called after left button click
     */
    public void setFunctionForLeftArrow(EventHandler<ActionEvent> functionToBeCalled){
        _leftButton.setOnAction(functionToBeCalled);
    }

    /**
     * Sets function which should be called after right button click
     * @param functionToBeCalled function which should be called after right button click
     */
    public void setFunctionForRightArrow(EventHandler<ActionEvent> functionToBeCalled){
        _rightButton.setOnAction(functionToBeCalled);
    }

    /**
     * Hides left button
     */
    public void hideLeftArrow(){
        _leftButton.setVisible(false);
    }

    /**
     * Shows left button
     */
    public void showLeftArrow(){
        _leftButton.setVisible(true);
    }

    /**
     * Hides right button
     */
    public void hideRightArrow(){
        _rightButton.setVisible(false);
    }

    /**
     * Shows right button
     */
    public void showRightArrow(){
        _rightButton.setVisible(true);
    }
}
