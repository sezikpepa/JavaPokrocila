package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundDraw;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RegistratedPlayersService;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RoundGeneration;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.SwissHandlers.SwissGenerator;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.*;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * Page where shown matches of selected rounds. It is also possible to generate the round.
 */
public class GeneratedRounds extends BasicPage {

    /**
     * Round which should be shown
     */
    private int _currentRound = 1;

    /**
     * Matches of the round
     */
    private RoundDraw _roundDraw = new RoundDraw();

    /**
     * Matches as components in the UI
     */
    private final ArrayList<RoundPairComponent> _roundPairComponents = new ArrayList<>();

    /**
     * Text which is shown in the UI when the draw is generated
     */
    private SpinnerWithText _loader;

    public GeneratedRounds(Main mainApp) {
        super(mainApp);
        reload();
    }

    /**
     * Generates matches for selected round
     */
    private void generateNewRound(){
        new RoundGeneration().generateRound(_currentRound);
    }

    /**
     * Retrieves saved matches and shows them in the UI
     */
    private void loadRoundDraw(){
        _roundDraw = new RoundGeneration().loadRoundDraw(_currentRound);

        _roundPairComponents.clear();

        for(var pair : _roundDraw.GetRoundPairs()){
            _roundPairComponents.add(new RoundPairComponent(pair));
        }
    }

    /**
     * Save results of matches
     */
    private void saveResults(){
        ArrayList<RoundPair> matches = new ArrayList<>();

        for(var component : _roundPairComponents){
            matches.add(component.RoundPair);
        }

        if(!matches.isEmpty()){
            new RoundGeneration().saveRoundDraw(_currentRound, new RoundDraw(matches));
        }
    }

    /**
     * Shows next round
     */
    private void moveToNextRound(){
        _currentRound++;
        _main.switchToGeneratedRounds();
    }

    /**
     * Shows previous round
     */
    private void moveToPreviousRound(){
        _currentRound--;
        _main.switchToGeneratedRounds();
    }

    /**
     * Reloads data in the component
     */
    public void reload(){

        Button buttonShowPodium = new BasicBlueButton("Show podium");
        buttonShowPodium.setOnAction(e -> _main.showPodium());

        Headline headline = new Headline("Round pairing");

        Button button = new BasicBlueButton("Show registered players");
        button.setOnAction(e -> _main.switchToRegisteredPlayers());

        Button resultsButton = new BasicBlueButton("Show results");
        resultsButton.setOnAction(e -> _main.switchToResultsPage());

        Button generateRoundButton = new Button("Generate round");
        generateRoundButton.setOnAction(e -> {
            _loader.setVisible(true);
            Task<Void> generateRoundTask = new Task<>() {
                @Override
                protected Void call() {
                    generateNewRound();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException x) {
                        System.out.println("Interupted");
                    }
                    _loader.setVisible(false);
                    reload();
                    _main.switchToGeneratedRounds();
                    return null;
                }
            };
            generateRoundTask.setOnSucceeded(event -> {
                _loader.setVisible(false);
                reload();
                _main.switchToGeneratedRounds();
            });
            generateRoundTask.setOnFailed(event -> {
                _loader.setVisible(false);
                reload();
                _main.switchToGeneratedRounds();
            });
            new Thread(generateRoundTask).start();
        });
        generateRoundButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-cursor: hand;");

        var buttonBox = new HBox(button, resultsButton, generateRoundButton, buttonShowPodium);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.TOP_LEFT);

        var registeredPlayers = new RegistratedPlayersService().getAllRegisteredPlayers();
        var generator = new SwissGenerator(registeredPlayers);

        Label currentRoundLabel = new Label("Round: " + _currentRound + " from " + generator.GetExpectedTournamentRounds());
        currentRoundLabel.setFont(new Font(30));

        Label messageLabel = new Label();

        Button _saveResultsButton = new Button("Save results");
        _saveResultsButton.setOnAction(e -> saveResults());
        _saveResultsButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-cursor: hand;");

        boolean roundDrawExists = new RoundGeneration().roundDrawExists(_currentRound);
        if(!roundDrawExists){
            _roundPairComponents.clear();
            _roundDraw = new RoundDraw();
            messageLabel.setText("Round draw does not exists");
            _saveResultsButton.setVisible(false);
        }
        else {
            messageLabel.setText("");
            loadRoundDraw();
            generateRoundButton.setVisible(false);
        }

        SwitchPages _switchPagesComponent = new SwitchPages();
        _switchPagesComponent.setFunctionForLeftArrow(e -> moveToPreviousRound());
        _switchPagesComponent.setFunctionForRightArrow(e -> moveToNextRound());

        if(_currentRound <= 1){
            _switchPagesComponent.hideLeftArrow();
        }


        if(_currentRound >= generator.GetExpectedTournamentRounds()){
            _switchPagesComponent.hideRightArrow();
        }

        _loader = new SpinnerWithText("Loading");
        _loader.setVisible(false);

        VBox layout = new VBox(headline, buttonBox, currentRoundLabel, _switchPagesComponent, messageLabel, _saveResultsButton, _loader);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.TOP_CENTER);

        var matchesBox = new VBox();
        for(var pair : _roundPairComponents){
            matchesBox.getChildren().add(pair);
        }
        var matchesScroll = new ScrollPane(matchesBox);
        layout.getChildren().add(matchesScroll);
        saveResults();


        scene = new Scene(layout, 800, 400);
    }

    /**
     * Returns scene for main app
     * @return scene
     */
    public Scene getScene(){
        reload();
        return scene;
    }
}
