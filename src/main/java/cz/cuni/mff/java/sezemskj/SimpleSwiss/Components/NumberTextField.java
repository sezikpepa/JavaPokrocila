package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * Text field where only numbers are allowed
 */
public class NumberTextField extends TextField {

    /**
     * Creates new text field
     */
    public NumberTextField(){
        setTextFormatter(new TextFormatter<>(new NumberFilter()));
    }

}
