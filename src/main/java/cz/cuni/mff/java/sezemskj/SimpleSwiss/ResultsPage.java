package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.BasicBlueButton;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.Headline;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.ResultsComponent;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RoundGeneration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ResultsPage extends BasicPage {

    private int _currentRound = 1;

    private ResultsComponent _resultsComponent;

    private Label _name;
    private Label _score;
    private Label _buchholz;
    private Label _buchholzShortened;
    private Label _sonnenbornBerger;
    private Label _blackPiecesPlayed;

    public ResultsPage(Main mainApp) {
        super(mainApp);
    }

    public void reload(){

        Headline headline = new Headline("Results");

        _name = new Label("Name");
        _name.setPrefWidth(300);
        _name.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _score = new Label("Score");
        _score.setPrefWidth(100);
        _score.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _buchholz = new Label("Buchholz");
        _buchholz.setPrefWidth(100);
        _buchholz.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _buchholzShortened = new Label("Buchholz shortened");
        _buchholzShortened.setPrefWidth(100);
        _buchholzShortened.setWrapText(true);
        _buchholzShortened.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _sonnenbornBerger = new Label("Sonnenborn Berger");
        _sonnenbornBerger.setPrefWidth(100);
        _sonnenbornBerger.setWrapText(true);
        _sonnenbornBerger.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        _blackPiecesPlayed = new Label("Black pieces");
        _blackPiecesPlayed.setPrefWidth(100);
        _blackPiecesPlayed.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        var labelsBox = new HBox(_name, _score, _buchholz, _buchholzShortened, _sonnenbornBerger, _blackPiecesPlayed);

        VBox layout = new VBox(headline, getButtonBox(), labelsBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);

        var generator = new RoundGeneration();
        _currentRound = generator.getMaximumRound();
        ArrayList<PlayerWithPoints> currentStandings = new RoundGeneration().loadCurrentStandings(_currentRound);

        currentStandings = currentStandings.stream()
                        .sorted(Comparator.comparing(PlayerWithPoints::GetScore).reversed()
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getBuchholz).reversed())
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getBuchholzShortened).reversed())
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getSonnenbornBerger).reversed())
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getBlackPiecesPlayed).reversed())).collect(Collectors.toCollection(ArrayList::new));

        for(var player : currentStandings){
            if(player.Player.isMule()){
                continue;
            }
            var component = new ResultsComponent();
            component.setResult(player);
            layout.getChildren().add(component);
        }

        scene = new Scene(layout, 800, 400);
    }

    private HBox getButtonBox() {
        Button button = new BasicBlueButton("Show registered players");
        button.setOnAction(e -> _main.switchToRegisteredPlayers());

        Button buttonRoundDraws = new BasicBlueButton("Show round draws");
        buttonRoundDraws.setOnAction(e -> _main.switchToGeneratedRounds());

        var buttonBox = new HBox(button, buttonRoundDraws);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        return buttonBox;
    }

    public Scene getScene() {
        return scene;
    }
}
