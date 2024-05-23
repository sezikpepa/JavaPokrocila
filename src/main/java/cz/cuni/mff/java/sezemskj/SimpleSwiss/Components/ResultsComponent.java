package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Results of the player with his/her name
 */
public class ResultsComponent extends HBox {

    /**
     * Name of the player
     */
    private final Label _name;
    /**
     * Points in the tournament
     */
    private final Label _score;
    /**
     * Auxiliary points of swiss system - Buchholz
     */
    private final Label _buchholz;
    /**
     * Auxiliary points of swiss system - BuchholzShortened
     */
    private final Label _buchholzShortened;
    /**
     * Auxiliary points of swiss system - SonnenbornBerger
     */
    private final Label _sonnenbornBerger;
    /**
     * Auxiliary points of swiss system - how many times player played with black pieces
     */
    private final Label _blackPiecesPlayed;

    /**
     * Initialize the component
     */
    public ResultsComponent() {
        this.setSpacing(10);

        _name = new Label();
        _name.setPrefWidth(300);
        _score = new Label();
        _score.setPrefWidth(100);
        _buchholz = new Label();
        _buchholz.setPrefWidth(100);
        _buchholzShortened = new Label();
        _buchholzShortened.setPrefWidth(100);
        _sonnenbornBerger = new Label();
        _sonnenbornBerger.setPrefWidth(100);
        _blackPiecesPlayed = new Label();
        _blackPiecesPlayed.setPrefWidth(100);

        this.getChildren().addAll(_name, _score, _buchholz, _buchholzShortened, _sonnenbornBerger, _blackPiecesPlayed);
    }

    /**
     * Sets the information to the labels
     * @param playerWithPoints Information about player performance
     */
    public void setResult(PlayerWithPoints playerWithPoints){
        _name.setText(playerWithPoints.Player.toString());
        _score.setText(String.valueOf(playerWithPoints.Score));
        _buchholz.setText(String.valueOf(playerWithPoints.Buchholz));
        _buchholzShortened.setText(String.valueOf(playerWithPoints.BuchholzShortened));
        _sonnenbornBerger.setText(String.valueOf(playerWithPoints.SonnenbornBerger));
        _blackPiecesPlayed.setText(String.valueOf(playerWithPoints.BlackPiecesPlayed));
    }


}
