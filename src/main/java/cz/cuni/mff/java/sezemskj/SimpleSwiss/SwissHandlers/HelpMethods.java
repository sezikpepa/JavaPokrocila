package cz.cuni.mff.java.sezemskj.SimpleSwiss.SwissHandlers;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundDraw;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelpMethods {

    public static ArrayList<PlayerWithPoints> ToPlayersWithPoints(ArrayList<Player> participants)
    {
        ArrayList<PlayerWithPoints> playersWithPoints = new ArrayList<>();

        for (Player player : participants)
        {
            playersWithPoints.add(new PlayerWithPoints(player));
        }

        return playersWithPoints;
    }

    public static boolean ContainsRoundPair(ArrayList<RoundPair> matches, RoundPair pairToCheck)
    {

        return matches.stream().anyMatch(x -> Objects.equals(x.White.Id, pairToCheck.White.Id) && Objects.equals(x.Black.Id, pairToCheck.Black.Id) || Objects.equals(x.White.Id, pairToCheck.Black.Id) && Objects.equals(x.Black.Id, pairToCheck.White.Id));
    }

    public static ArrayList<RoundPair> GetAllMatches(ArrayList<RoundDraw> roundDraws)
    {
        ArrayList<RoundPair> allPairs = new ArrayList<>();
        for (var roundDraw : roundDraws)
        {
            allPairs.addAll(roundDraw.GetRoundPairs());
        }

        return allPairs;
    }

    public static ArrayList<RoundPair> GetMatchesOfParticipant(ArrayList<RoundPair> matches, Player participant)
    {
        return matches.stream().filter(x -> x.White.Id.equals(participant.Id) || x.Black.Id.equals(participant.Id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean IsPaired(ArrayList<RoundPair> matches, Player participantToCheck)
    {
        if(participantToCheck == null)
        {
            return matches.stream().anyMatch(x -> x.White == null || x.Black == null);
        }

        return matches.stream().anyMatch(x -> (x.White != null && x.White.Id.equals(participantToCheck.Id)) || (x.Black != null && x.Black.Id.equals(participantToCheck.Id)));
    }

    public static ArrayList<Player> GetAllParticipants(ArrayList<RoundPair> matches)
    {
        ArrayList<Player> participants = new ArrayList<>();

        for (var match : matches)
        {
            participants.add(match.White);
            participants.add(match.Black);
        }

        return participants;
    }


}
