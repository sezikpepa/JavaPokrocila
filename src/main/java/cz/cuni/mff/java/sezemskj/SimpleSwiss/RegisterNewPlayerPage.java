package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.BasicBlueButton;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.Headline;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.NumberTextField;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.SpinnerWithText;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RegistratedPlayersService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static java.lang.Integer.parseInt;

public class RegisterNewPlayerPage extends BasicPage{

    private SpinnerWithText _spinner;

    TextField nameField;
    TextField lastNameField;
    TextField chessClubField;
    NumberTextField eloField;
    Button submitButton;

    Button button;

    private void setEnableToActionComponents(boolean enable){
        nameField.setDisable(!enable);
        lastNameField.setDisable(!enable);
        chessClubField.setDisable(!enable);
        eloField.setDisable(!enable);
        submitButton.setDisable(!enable);
        button.setDisable(!enable);
    }

    public RegisterNewPlayerPage(Main mainApp) {
        super(mainApp);

        Headline headline = new Headline("Register new player");

        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);

        button = new BasicBlueButton("Go back to all players");
        button.setOnAction(e -> mainApp.switchToRegisteredPlayers());


        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label lastNameLabel = new Label("Surname:");
        lastNameField = new TextField();

        Label chessClubLabel = new Label("ChessClub:");
        chessClubField = new TextField();

        Label eloLabel = new Label("Elo:");
        eloField = new NumberTextField();
        eloField.setText("1000");

        submitButton = new Button("Register");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-cursor: hand;");

        _spinner = new SpinnerWithText("Saving");
        _spinner.setVisible(false);

        submitButton.setOnAction(e -> {
            _spinner.setVisible(true);
            setEnableToActionComponents(false);
            Player playerToRegister = new Player();
            playerToRegister.FirstName = nameField.getText();
            playerToRegister.LastName = lastNameField.getText();
            playerToRegister.ChessClub = chessClubField.getText();
            playerToRegister.Elo = parseInt(eloField.getText());


            Task<Void> registrationTask = new Task<>() {
                @Override
                protected Void call() {
                    new RegistratedPlayersService().registerNewPlayer(playerToRegister);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException c) {
                        System.out.println("interupted");
                    }
                    return null;
                }
            };
            registrationTask.setOnSucceeded(event -> {
                _spinner.setText("Change");
                nameField.setText("");
                lastNameField.setText("");
                chessClubField.setText("");
                eloField.setText("1000");
                _spinner.setVisible(false);
                setEnableToActionComponents(true);
            });

            registrationTask.setOnFailed(event -> {
                _spinner.setVisible(false);
            });
            new Thread(registrationTask).start();
        });

        VBox vbox = new VBox(10, headline, nameLabel, nameField, lastNameLabel, lastNameField, chessClubLabel, chessClubField, eloLabel, eloField, submitButton, _spinner, button);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        vbox.setAlignment(Pos.CENTER);
        scene = new Scene(vbox, 800, 400);
    }

    public Scene getScene(){
        return scene;
    }

}
