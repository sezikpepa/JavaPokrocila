package cz.cuni.mff.java.sezemskj.SimpleSwiss.SwissHandlers;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundDraw;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;

import java.util.ArrayList;
import java.util.Objects;

public class AuxiliaryPointsCalculators {

    public static ArrayList<PlayerWithPoints> CalculateAllAuxiliaryPoints(ArrayList<PlayerWithPoints> participants, ArrayList<RoundDraw> roundDraws)
    {
        participants = CalculateBlackPiecesPlayed(participants, roundDraws);
        participants = CalculateBuchholz(participants, roundDraws);
        participants = CalculateBuchholzShortened(participants, roundDraws);
        participants = CalculateSonnenBornBerger(participants, roundDraws);

        return participants;
    }

    public static ArrayList<PlayerWithPoints> CalculateBlackPiecesPlayed(ArrayList<PlayerWithPoints> participants, ArrayList<RoundDraw> roundDraws)
    {
        for (var participant : participants)
        {
            participant.BlackPiecesPlayed = 0;
        }


        for (var roundDraw : roundDraws)
        {
            ArrayList<RoundPair> pairs = roundDraw.GetRoundPairs();

            for (var pair : pairs)
            {
                if(participants.stream().anyMatch(x -> x.Player.Id.equals(pair.Black.Id)))
                {
                    participants.stream().filter(x -> x.Player.Id.equals(pair.Black.Id)).findFirst().get().BlackPiecesPlayed++;
                }
            }
        }

        return participants;
    }
    public static ArrayList<PlayerWithPoints> CalculateBuchholz(ArrayList<PlayerWithPoints> participants, ArrayList<RoundDraw> roundDraws)
    {
        for (var participant : participants)
        {
            participant.Buchholz = 0;
        }

        ArrayList<RoundPair> allPairs = HelpMethods.GetAllMatches(roundDraws);

        for (var participant : participants)
        {
            ArrayList<RoundPair> participantsMatches = HelpMethods.GetMatchesOfParticipant(allPairs, participant.Player);

            for (var match : participantsMatches)
            {
                var tempScore = participants.stream().filter(x -> Objects.equals(x.Player.Id, match.GetOpponent(participant.Player).Id)).findFirst().orElse(null);
                if(tempScore != null){
                    participant.Buchholz += tempScore.Score;
                }

            }
        }

        return participants;
    }

    public static ArrayList<PlayerWithPoints> CalculateBuchholzShortened(ArrayList<PlayerWithPoints> participants, ArrayList<RoundDraw> roundDraws)
    {
        for (var participant : participants)
        {
            participant.BuchholzShortened = 0;
        }

        ArrayList<RoundPair> allPairs = HelpMethods.GetAllMatches(roundDraws);

        for (var participant : participants)
        {
            ArrayList<RoundPair> participantsMatches = HelpMethods.GetMatchesOfParticipant(allPairs, participant.Player);

            if (participantsMatches.size() <= 2)
            {
                continue;
            }


            double minOpponentScore = 0;
            double maxOpponentScore = 0;

            for (var match : participantsMatches)
            {
                double opponentScore = 0;
                var tempScore = participants.stream().filter(x -> Objects.equals(x.Player.Id, match.GetOpponent(participant.Player).Id)).findFirst().orElse(null);
                if(tempScore != null) {
                    opponentScore = tempScore.Score;
                }

                minOpponentScore = Math.min(opponentScore, minOpponentScore);
                maxOpponentScore = Math.max(opponentScore, maxOpponentScore);

                participant.BuchholzShortened += opponentScore;
            }

            participant.BuchholzShortened -= minOpponentScore + maxOpponentScore;

        }

        return participants;
    }

    public static ArrayList<PlayerWithPoints> CalculateSonnenBornBerger(ArrayList<PlayerWithPoints> participants, ArrayList<RoundDraw> roundDraws)
    {
        for (var participant : participants)
        {
            participant.SonnenbornBerger = 0;
        }

        ArrayList<RoundPair> allPairs = HelpMethods.GetAllMatches(roundDraws);

        for (var participant : participants)
        {
            ArrayList<RoundPair> participantsMatches = HelpMethods.GetMatchesOfParticipant(allPairs, participant.Player);

            for (var match : participantsMatches)
            {
                double opponentScore = 0;
                var tempScore = participants.stream().filter(x -> Objects.equals(x.Player.Id, match.GetOpponent(participant.Player).Id)).findFirst().orElse(null);
                if(tempScore != null) {
                    opponentScore = tempScore.Score;
                }
                double participantScoreFromMatch = match.GetScoreOfParticipant(participant.Player);

                participant.SonnenbornBerger += opponentScore * participantScoreFromMatch;
            }
        }

        return participants;
    }
}
