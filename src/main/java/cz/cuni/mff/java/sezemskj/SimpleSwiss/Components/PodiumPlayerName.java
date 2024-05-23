package cz.cuni.mff.java.sezemskj.SimpleSwiss.Components;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import javafx.scene.text.Font;

/**
 * Name of the players which is shown on podium
 */
public class PodiumPlayerName extends PodiumLabel {

    /**
     * Creates new label
     * @param player Information about the player shown on the label
     * @param x X coordinate
     * @param y Y coordinate
     * @param font Font of the text
     * @param durationInSeconds How many seconds the animation should last
     */
    public PodiumPlayerName(Player player, int x, int y, Font font, int durationInSeconds){
        super(player != null ? player.FirstName.charAt(0) + ". " + player.LastName : "", x , y, font, durationInSeconds);
    }

}
