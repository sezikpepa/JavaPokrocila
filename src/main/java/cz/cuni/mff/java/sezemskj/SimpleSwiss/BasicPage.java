package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import javafx.scene.Scene;

/**
 * Every page in the application
 */
public class BasicPage {

    /**
     * Scene - scene of javafx
     */
    protected Scene scene;

    /**
     * Application - shared data are stored in this variable
     */
    protected Main _main;

    /**
     * Creates new basic page
     * @param main Application - shared data are stored in this variable
     */
    public BasicPage(Main main){
        _main = main;
    }
}
