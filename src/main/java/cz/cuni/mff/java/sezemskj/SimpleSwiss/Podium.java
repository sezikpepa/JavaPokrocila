package cz.cuni.mff.java.sezemskj.SimpleSwiss;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.BasicBlueButton;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.GrowingLine;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.PodiumLabel;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Components.PodiumPlayerName;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices.RoundGeneration;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Shows animation of the podium with the best players of the tournament
 */
public class Podium extends BasicPage {

    /**
     * Initialize the component
     * @param main Main application
     */
    public Podium(Main main){
        super(main);
    }

    /**
     * Initialize all components and starts the animation process
     */
    public void reload(){

        Button backButton = new BasicBlueButton("Back");
        backButton.setOnAction(x -> _main.switchToGeneratedRounds());
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);

        GrowingLine secondPlaceLeft = new GrowingLine(100, 360, 100, 230, 2);
        GrowingLine thirdPlaceRight = new GrowingLine(700, 360, 700, 230, 2);

        GrowingLine secondPlaceTop = new GrowingLine(100, 230, 300 , 230, 2);
        GrowingLine thirdPlaceTop = new GrowingLine(700, 230, 500 , 230, 2);

        GrowingLine firstPlaceLeft = new GrowingLine(300, 230, 300, 100, 2);
        GrowingLine firstPlaceRight = new GrowingLine(500, 230, 500, 100, 2);

        GrowingLine firstPlaceTopLeft = new GrowingLine(300, 100, 400, 100, 2);
        GrowingLine firstPlaceTopRight = new GrowingLine(500, 100, 400, 100, 2);

        Font labelsFont = Font.font("Arial", FontWeight.BOLD, 80);

        PodiumLabel firstLabel = new PodiumLabel("1", 380, 120, labelsFont, 2);
        PodiumLabel secondLabel = new PodiumLabel("2", 190, 250, labelsFont, 2);
        PodiumLabel thirdLabel = new PodiumLabel("3", 590, 250, labelsFont, 2);

        ArrayList<PlayerWithPoints> standings = new RoundGeneration().loadCurrentStandings(new RoundGeneration().getMaximumRound());

        standings = standings.stream()
                        .sorted(Comparator.comparing(PlayerWithPoints::GetScore).reversed()
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getBuchholz).reversed())
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getBuchholzShortened).reversed())
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getSonnenbornBerger).reversed())
                        .thenComparing(Comparator.comparing(PlayerWithPoints::getBlackPiecesPlayed).reversed())).collect(Collectors.toCollection(ArrayList::new));

        Font playersFont = Font.font("Arial", FontWeight.BOLD, 25);

        //it is if else, because variables need to be "final" otherwise executor will not work
        PodiumPlayerName firstPlayerLabel;
        if(!standings.isEmpty()){
            double width = getWidthOfText(standings.get(0).Player.FirstName.charAt(0) + ". " + standings.get(0).Player.LastName, playersFont);
            firstPlayerLabel = new PodiumPlayerName(standings.get(0).Player, (int) (400 - width/2), 60, playersFont, 2);
        }
        else {
            firstPlayerLabel = null;
        }

        PodiumPlayerName secondPlayerLabel;
        if(standings.size() > 1){
            double width = getWidthOfText(standings.get(1).Player.FirstName.charAt(0) + ". " + standings.get(1).Player.LastName, playersFont);
            secondPlayerLabel = new PodiumPlayerName(standings.get(1).Player, (int) (285 - width), 190, playersFont, 2);
        }
        else{
            secondPlayerLabel = null;
        }

        PodiumPlayerName thirdPlayerLabel;
        if(standings.size() > 2){
            thirdPlayerLabel = new PodiumPlayerName(standings.get(2).Player, 515, 190, playersFont, 2);
        } else {
            thirdPlayerLabel = null;
        }


        Group root = new Group(secondPlaceLeft,
                thirdPlaceRight,
                secondPlaceTop,
                thirdPlaceTop,
                firstPlaceLeft,
                firstPlaceRight,
                firstPlaceTopLeft,
                firstPlaceTopRight,
                firstLabel,
                secondLabel,
                thirdLabel,
                firstPlayerLabel,
                secondPlayerLabel,
                thirdPlayerLabel,
                backButton);

        scene = new Scene(root, 800, 400);

        playAllAnimations(secondPlaceLeft,
                thirdPlaceRight,
                secondPlaceTop,
                thirdPlaceTop,
                firstPlaceLeft,
                firstPlaceRight,
                firstPlaceTopLeft,
                firstPlaceTopRight,
                firstLabel,
                secondLabel,
                thirdLabel,
                thirdPlayerLabel,
                secondPlayerLabel,
                firstPlayerLabel);
    }


    /**
     * Returns with of the text in pixels
     * @param text Text which will be measured
     * @param font Font of the text
     * @return Width of the text in pixels
     */
    private int getWidthOfText(String text, Font font){
        var tmp = new Text(text);
        tmp.setFont(font);
        return (int) tmp.getBoundsInLocal().getWidth();
    }

    /**
     * Starts the animation process
     * @param secondPlaceLeft Label
     * @param thirdPlaceRight Label
     * @param secondPlaceTop Label
     * @param thirdPlaceTop Label
     * @param firstPlaceLeft Label
     * @param firstPlaceRight Label
     * @param firstPlaceTopLeft Label
     * @param firstPlaceTopRight Label
     * @param firstLabel Label
     * @param secondLabel Label
     * @param thirdLabel Label
     * @param thirdPlayerLabel Label
     * @param secondPlayerLabel Label
     * @param firstPlayerLabel Label
     */
    private void playAllAnimations(GrowingLine secondPlaceLeft,
                                   GrowingLine thirdPlaceRight,
                                   GrowingLine secondPlaceTop,
                                   GrowingLine thirdPlaceTop,
                                   GrowingLine firstPlaceLeft,
                                   GrowingLine firstPlaceRight,
                                   GrowingLine firstPlaceTopLeft,
                                   GrowingLine firstPlaceTopRight,
                                   PodiumLabel firstLabel,
                                   PodiumLabel secondLabel,
                                   PodiumLabel thirdLabel,
                                   PodiumPlayerName thirdPlayerLabel,
                                   PodiumPlayerName secondPlayerLabel,
                                   PodiumPlayerName firstPlayerLabel){

        secondPlaceLeft.play();
        thirdPlaceRight.play();

        secondPlaceTop.play();
        thirdPlaceTop.play();

        firstPlaceLeft.play();
        firstPlaceRight.play();
        firstPlaceTopLeft.play();
        firstPlaceTopRight.play();

        firstLabel.play();
        secondLabel.play();
        thirdLabel.play();

        showPodiumPlayerNameWithDelay(thirdPlayerLabel, 3);
        showPodiumPlayerNameWithDelay(secondPlayerLabel, 6);
        showPodiumPlayerNameWithDelay(firstPlayerLabel, 9);
    }

    private void showPodiumPlayerNameWithDelay(PodiumPlayerName podiumPlayerName, int delayInSeconds){
        if(podiumPlayerName == null){
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(delayInSeconds));
        pause.setOnFinished(x -> {
            podiumPlayerName.play();
        });
        pause.play();
    }

    public Scene getScene() {
        return scene;
    }

}
