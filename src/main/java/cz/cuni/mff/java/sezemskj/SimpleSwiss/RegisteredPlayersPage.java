package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.BasicBlueButton;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.Headline;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.RegisteredPlayerComponent;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RegistratedPlayersService;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.TournamentService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Page which shows all registered players in the tournament. It is also possible to remove them if the tournament has not yet started
 */
public class RegisteredPlayersPage extends BasicPage {

    private final ArrayList<RegisteredPlayerComponent> _registeredPlayersComponents = new ArrayList<>();

    private VBox _vbox;

    private Button _registerNewPlayerButton;

    public RegisteredPlayersPage(Main mainApp) {
        super(mainApp);
        reload();
    }

    public void reload(){

        Headline headline = new Headline("Registered players");

        HBox buttonBox = initializeMenuButtons();

        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.setSpacing(10);

        _vbox = new VBox(10, headline, buttonBox);
        _vbox.setAlignment(Pos.TOP_CENTER);
        _vbox.setPadding(new Insets(10, 10, 10, 10));

        loadAllPlayers();
        disablePlayerDeleteAndRegisterNewPlayerIfTournamentStarted();

        ScrollPane scrollable = new ScrollPane(_vbox);

        scene = new Scene(scrollable, 800, 400);
    }

    private HBox initializeMenuButtons(){
        Button button = new BasicBlueButton("Show rounds");
        button.setOnAction(e -> _main.switchToGeneratedRounds());
        var registeredPlayers = new RegistratedPlayersService().getAllRegisteredPlayers();
        if(registeredPlayers.size() <= 2){
            button.setDisable(true);
        }

        _registerNewPlayerButton = new Button("Register new player");
        _registerNewPlayerButton.setOnAction(e -> _main.switchToRegisterNewPlayer());
        _registerNewPlayerButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-cursor: hand;");

        Button resultsButton = new BasicBlueButton("Show results");
        resultsButton.setOnAction(e -> _main.switchToResultsPage());
        if(registeredPlayers.size() <= 2){
            resultsButton.setDisable(true);
        }

        Button settingsButton = new BasicBlueButton("Show settings");
        settingsButton.setOnAction(e -> _main.showSettingsPage());


        return new HBox(settingsButton, button, resultsButton, _registerNewPlayerButton);
    }

    public void disablePlayerDeleteAndRegisterNewPlayerIfTournamentStarted(){
        var tournamentService = new TournamentService();
        boolean tournamentStarted = tournamentService.TournamentHasStarted();

        if(tournamentStarted){
            for(var component : _registeredPlayersComponents){
                component.disableDelete();
            }

            _registerNewPlayerButton.setVisible(false);
        }
    }

    private void loadAllPlayers(){
        var registratedPlayerService = new RegistratedPlayersService();
        ArrayList<Player> _registeredPlayers = registratedPlayerService.getAllRegisteredPlayers();
        _registeredPlayersComponents.clear();
        for(var player : _registeredPlayers){
            if(player.isMule()){
                continue;
            }
            RegisteredPlayerComponent playerRecord = new RegisteredPlayerComponent(player, _main);
            _registeredPlayersComponents.add(playerRecord);
            _vbox.getChildren().add(playerRecord);
        }
    }

    public Scene getScene(){
        reload();
        return scene;
    }
}
