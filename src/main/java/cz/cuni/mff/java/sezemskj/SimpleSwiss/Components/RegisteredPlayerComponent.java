package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RegistratedPlayersService;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RegisteredPlayerComponent extends HBox {

    private final Label label;
    private final Button button;
    private final Player _player;
    private final RegistratedPlayersService _registratedPlayersService;
    private final Main _application;

    public RegisteredPlayerComponent(Player player, Main application) {
        super();

        _registratedPlayersService = new RegistratedPlayersService();
        _application = application;
        _player = player;

        label = new Label(_player.toString());
        label.setPrefWidth(500);
        label.setPadding(new Insets(0, 0, 0, 40));

        button = new Button("X");
        button.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");

        getChildren().addAll(label, button);

        setSpacing(10);

        button.setOnAction(event -> {
            _registratedPlayersService.removePlayerFromTournament(_player.Id);
            _application.switchToRegisteredPlayers();
            System.out.println("Button clicked!");
        });
    }
    public void setPlayer(Player player) {
        label.setText(player.toString());
    }

    public void disableDelete(){
        button.setVisible(false);
    }
}
