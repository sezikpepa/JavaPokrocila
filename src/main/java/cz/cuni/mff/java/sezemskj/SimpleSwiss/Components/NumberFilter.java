package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

/**
 * Filter which enables only numbers for textfield
 */
public class NumberFilter implements UnaryOperator<TextFormatter.Change> {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        String candidateForNewText = change.getControlNewText();
        if (candidateForNewText.matches("[0-9]+")) {
            return change;
        }
        return null;
    }
}
