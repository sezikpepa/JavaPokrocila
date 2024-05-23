package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Javafx label with already set styles - headline
 */
public class Headline extends Label {

    /**
     * Creates new headline
     * @param text Text which will be displayed
     */
    public Headline(String text){
        super();
        setText(text);
        setFont(Font.font("Arial", FontWeight.BOLD, 25));
    }
}
