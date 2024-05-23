package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

/**
 * Subcomponent for matches - both players and result are shown
 */
public class RoundPairComponent extends HBox {

    /**
     * Match with result
     */
    public RoundPair RoundPair;

    /**
     * Component where it is possible to change result
     */
    private final ComboBox<String> _resultOfTheMatch;

    /**
     * Sets result to match, as selected in combo-box
     */
    private void setMatchResult(){
        String result = _resultOfTheMatch.getValue();

        var splited = result.split(":");

        RoundPair.WhitePoints = Double.parseDouble(splited[0]);
        RoundPair.BlackPoints = Double.parseDouble(splited[1]);

    }

    /**
     * Initialize the component
     * @param roundPair Information to show - both players and the result
     */
    public RoundPairComponent(RoundPair roundPair) {

        RoundPair = roundPair;

        Label _white = new Label(roundPair.White.toString());
        Label _black = new Label(roundPair.Black.toString());

        _white.setPrefWidth(305);
        _black.setPrefWidth(300);

        _white.setTextAlignment(TextAlignment.LEFT);
        _black.setTextAlignment(TextAlignment.RIGHT);
        _black.setAlignment(Pos.CENTER_RIGHT);

        _resultOfTheMatch = new ComboBox<>();
        _resultOfTheMatch.getItems().addAll("0:0", "1:0", "0:1", "0.5:0.5");
        _resultOfTheMatch.setPrefWidth(120);
        _resultOfTheMatch.setStyle("-fx-alignment: center-right; -fx-background-color: lightblue; -fx-cursor: hand;");

        String toShow = "";
        if(roundPair.WhitePoints < 0.1 && roundPair.BlackPoints < 0.1){
            toShow = "0:0";
        }
        else if(roundPair.WhitePoints > 0.9 && roundPair.BlackPoints < 0.1){
            toShow = "1:0";
        }
        else if(roundPair.WhitePoints < 0.1 && roundPair.BlackPoints > 0.9){
            toShow = "0:1";
        }
        else if(roundPair.WhitePoints > 0.1 && roundPair.WhitePoints < 0.6 && roundPair.BlackPoints > 0.1 && roundPair.BlackPoints < 0.6){
            toShow = "0.5:0.5";
        }

        if(roundPair.Black.isMule()){
            toShow = "1:0";
            RoundPair.WhitePoints = 1;
            RoundPair.BlackPoints = 0;
            _resultOfTheMatch.setDisable(true);
        }
        else if(roundPair.White.isMule()){
            toShow = "0:1";
            RoundPair.WhitePoints = 0;
            RoundPair.BlackPoints = 1;
            _resultOfTheMatch.setDisable(true);
        }

        _resultOfTheMatch.getSelectionModel().select(toShow);
        _resultOfTheMatch.setOnAction(e -> setMatchResult());

        setSpacing(10);
        setPadding(new Insets(10));

        getChildren().addAll(_white, _resultOfTheMatch, _black);
    }
}
