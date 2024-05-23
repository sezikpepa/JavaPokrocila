package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.LocationService;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * Core of the application, handles showing of different pages
 */
public class Main extends Application {
    private Stage primaryStage;
    public RegisteredPlayersPage RegisteredPlayers;
    public GeneratedRounds GeneratedRoundsPage;
    public ResultsPage ResultsPage;
    public RegisterNewPlayerPage RegisterNewPlayer;
    public SettingsPage SettingsPage;
    public Podium PodiumPage;
    public File FolderWithData;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Simple swiss");

        Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/mainIcon.png")));
        primaryStage.getIcons().add(mainIcon);

        RegisteredPlayers = new RegisteredPlayersPage(this);
        GeneratedRoundsPage = new GeneratedRounds(this);
        ResultsPage = new ResultsPage(this);
        RegisterNewPlayer = new RegisterNewPlayerPage(this);
        SettingsPage = new SettingsPage(this);
        PodiumPage = new Podium(this);

        var path = new LocationService().getPath();
        if(path == null){
            showSettingsPage();
        }
        else {
            switchToRegisteredPlayers();
        }

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchToRegisteredPlayers(){
        RegisteredPlayers.reload();
        primaryStage.setScene(RegisteredPlayers.getScene());
    }

    public void switchToRegisterNewPlayer(){
        primaryStage.setScene(RegisterNewPlayer.getScene());
    }

    public void switchToGeneratedRounds(){
        GeneratedRoundsPage.reload();
        primaryStage.setScene(GeneratedRoundsPage.getScene());
    }

    public void switchToResultsPage(){
        ResultsPage.reload();
        primaryStage.setScene(ResultsPage.getScene());
    }

    public void showSettingsPage(){
        SettingsPage.reload();
        primaryStage.setScene(SettingsPage.getScene());
    }

    public void showPodium(){
        PodiumPage.reload();
        primaryStage.setScene(PodiumPage.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
