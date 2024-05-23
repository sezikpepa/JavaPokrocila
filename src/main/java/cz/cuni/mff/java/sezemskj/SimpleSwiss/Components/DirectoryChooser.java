package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;


import javafx.stage.Stage;
import java.io.File;
import java.util.function.Consumer;

/**
 * Enables to select directory, where files about the tournament are stored
 */
public class DirectoryChooser {

    /**
     * Popup where it is possible to select directory
     */
    private javafx.stage.DirectoryChooser directoryChooser;

    /**
     * Folder selection by this component by user
     */
    private File _selectedFolder;

    /**
     * Returns selected folder
     * @return selected folder
     */
    public File getSelectedFolder(){
        return _selectedFolder;
    }

    /**
     * Creates new component to select directory
     */
    public DirectoryChooser(){
        directoryChooser = new javafx.stage.DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
    }

    /**
     * Shows the popup window with selection
     * @param callback Function which should be called, where folder is selected
     */
    public void show(Consumer<File> callback){
        Stage stage = new Stage();
        _selectedFolder = directoryChooser.showDialog(stage);

        if (_selectedFolder != null) {
            callback.accept(_selectedFolder);
        }
    }


}
