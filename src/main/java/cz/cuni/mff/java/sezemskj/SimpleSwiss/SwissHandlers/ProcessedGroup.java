package cz.cuni.mff.java.sezemskj.SimpleSwiss.SwissHandlers;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;

import java.util.ArrayList;

/**
 * Group of swiss system which was sorted into pairs and leftover
 */
public class ProcessedGroup {

    public ProcessedGroup(ArrayList<RoundPair> item1, ArrayList<PlayerWithPoints> item2){
        Item1 = item1;
        Item2 = item2;
    }

    /**
     * Generated pairs
     */
    public ArrayList<RoundPair> Item1;
    /**
     * Players without pair
     */
    public ArrayList<PlayerWithPoints> Item2;


}
