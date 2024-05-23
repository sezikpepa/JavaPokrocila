package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.DirectoryChooser;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.Headline;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.LocationService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;

public class SettingsPage extends BasicPage {

    public SettingsPage(Main mainApp) {
        super(mainApp);
        reload();
    }

    public void reload(){

        Button button = new Button("Show registered players");
        button.setOnAction(e -> _main.switchToRegisteredPlayers());
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-cursor: hand;");

        Headline headline = new Headline("Settings");

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Label selectedFolderLabel = new Label();
        File folder = new LocationService().getPath();
        if(folder != null){
            selectedFolderLabel.setText(folder.getAbsolutePath());
        }
        else{
            button.setVisible(false);
        }

        Button selectFolder = new Button("Select folder with files");
        selectFolder.setOnAction(e -> directoryChooser.show(selectedFolder -> {
            if (selectedFolder != null) {
                selectedFolderLabel.setText(selectedFolder.getAbsolutePath());
                _main.FolderWithData = selectedFolder.getAbsoluteFile();
                new LocationService().savePath(_main.FolderWithData);
                button.setVisible(true);
            }
        }));

        File path = directoryChooser.getSelectedFolder();
        if(path != null){
            System.out.println(path.getAbsolutePath());
        }

        VBox _vbox = new VBox(10, button, headline, selectFolder, selectedFolderLabel);
        _vbox.setAlignment(Pos.TOP_CENTER);
        _vbox.setPadding(new Insets(10, 10, 10, 10));

        scene = new Scene(_vbox, 800, 400);
    }


    public Scene getScene() {
        reload();
        return scene;
    }
}
