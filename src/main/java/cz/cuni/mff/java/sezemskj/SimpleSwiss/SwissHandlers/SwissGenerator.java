package cz.cuni.mff.java.sezemskj.SimpleSwiss.SwissHandlers;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.BasicStructures.Tuple;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundDraw;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SwissGenerator {

    private ArrayList<Player> _players = new ArrayList<>();

    public int GetExpectedTournamentRounds(){
        return (int) Math.ceil(Math.log((long) _players.size()) / Math.log(2));
    }

    public SwissGenerator(ArrayList<Player> players) {
        _players = players;
    }

    public RoundDraw GenerateRoundDraw(int roundNumber, ArrayList<RoundDraw> roundDraws)
    {
        return GetRoundDraw(roundNumber, roundDraws, _players);
    }

    protected void AddPointsToParticipant(PlayerWithPoints participant, RoundPair match)
    {
        if(participant == null)
        {
            return;
        }

        participant.AddScore(match.GetScoreOfParticipant(participant.Player));
    }

    public ArrayList<PlayerWithPoints> GetCurrentStandings(ArrayList<RoundDraw> roundDraws, boolean calculateAuxiliaryPoints)
    {
        ArrayList<PlayerWithPoints> playersWithPoints = HelpMethods.ToPlayersWithPoints(_players);

        for (RoundDraw roundDraw : roundDraws)
        {
            for (RoundPair pair : roundDraw.GetRoundPairs())
            {
                Optional<PlayerWithPoints> white = playersWithPoints.stream().filter(x -> x.Player.Id.equals(pair.White.Id)).findFirst();
                white.ifPresent(playerWithPoints -> AddPointsToParticipant(playerWithPoints, pair));
                Optional<PlayerWithPoints> black = playersWithPoints.stream().filter(x -> x.Player.Id.equals(pair.Black.Id)).findFirst();
                black.ifPresent(playerWithPoints -> AddPointsToParticipant(playerWithPoints, pair));
            }
        }

        if (calculateAuxiliaryPoints)
        {
            playersWithPoints = AuxiliaryPointsCalculators.CalculateAllAuxiliaryPoints(playersWithPoints, roundDraws);
        }

        return playersWithPoints.stream().sorted(Comparator.comparingDouble(PlayerWithPoints::GetScore).reversed()).collect(Collectors.toCollection(ArrayList::new));
    }

    public RoundDraw GetRoundDraw(int roundNumber, ArrayList<RoundDraw> previousDraws, ArrayList<Player> participants)
    {

        if(participants.size() % 2 != 0)
        {
            participants.add(null);
        }


        RoundDraw finalRoundDraw = new RoundDraw();

        ArrayList<ArrayList<PlayerWithPoints>> pointsGroups = SortIntoGroups(participants, previousDraws);

        ArrayList<ArrayList<RoundPair>> groups = new ArrayList<>();

        ArrayList<PlayerWithPoints> currentLeftOver = new ArrayList<>();

        for(int i = 0; i < pointsGroups.size(); i++)
        {
            ProcessedGroup result = ProcessGroup(HelpMethods.GetAllMatches(previousDraws), pointsGroups.get(i), currentLeftOver);

            if(!result.Item1.isEmpty())
            {
                groups.add(result.Item1);
            }
            currentLeftOver = result.Item2;
        }


        if (!currentLeftOver.isEmpty())
        {
            ArrayList<ArrayList<RoundPair>> lastGroups = new ArrayList<>();
            while (!groups.isEmpty())
            {
                lastGroups.add(groups.get(groups.size() - 1));
                groups.remove(groups.size() - 1);

                ArrayList<RoundPair> attemptForLastGroup = GenerateCollapsedGroup(lastGroups, previousDraws, currentLeftOver);

                if (attemptForLastGroup != null)
                {
                    groups.add(attemptForLastGroup);
                    break;
                }
            }
        }

        var participantsWithColor = HelpMethods.ToPlayersWithPoints(participants);
        participantsWithColor = AuxiliaryPointsCalculators.CalculateBlackPiecesPlayed(participantsWithColor, previousDraws);

        for (int i = groups.size() - 1; i >= 0; i--)
        {
            for (RoundPair pair : groups.get(i))
            {
                Optional<Double> participantBlackCountWhiteTemp = participantsWithColor.stream()
                        .filter(x -> x.Player != null && x.Player.Id.equals(pair.White.Id))
                        .findFirst()
                        .map(PlayerWithPoints::getBlackPiecesPlayed);

                double participantBlackCountWhite = participantBlackCountWhiteTemp.orElse(0.0);

                Optional<Double> participantBlackCountBlackTemp = participantsWithColor.stream()
                        .filter(x -> x.Player != null && x.Player.Id.equals(pair.Black.Id))
                        .findFirst()
                        .map(PlayerWithPoints::getBlackPiecesPlayed);

                double participantBlackCountBlack = participantBlackCountBlackTemp.orElse(0.0);

                if(participantBlackCountBlack > participantBlackCountWhite)
                {
                    pair.SwapParticipants();
                }
            }
        }

        for (int i = groups.size() - 1; i >= 0; i--)
        {
            for (RoundPair pair : groups.get(i))
            {
                pair.WhitePoints = 0;
                pair.BlackPoints = 0;
                if (pair.White == null)
                {
                    pair.SwapParticipants();
                }
                if (pair.Black == null)
                {
                    pair.WhitePoints = 1;
                    pair.BlackPoints = 0;
                }
            }
            finalRoundDraw.AddRange(groups.get(i));
        }



        return finalRoundDraw;
    }

    private ArrayList<RoundPair> GenerateCollapsedGroup(ArrayList<ArrayList<RoundPair>> groupsToUse, ArrayList<RoundDraw> previousDraws, ArrayList<PlayerWithPoints> leftover)
    {
        ArrayList<Player> participantsToUse = leftover.stream().map(x -> x.Player).collect(Collectors.toCollection(ArrayList::new));
        for (ArrayList<RoundPair> group : groupsToUse)
        {
            participantsToUse.addAll(HelpMethods.GetAllParticipants(group) );

        }

        if(participantsToUse.size() % 2 != 0)
        {
            return null;
        }
        return TryAllParticipantCombinations(participantsToUse, previousDraws);
    }

    public static ArrayList<ArrayList<Player>> generatePermutations(ArrayList<Player> list) {
        ArrayList<ArrayList<Player>> result = new ArrayList<>();
        permute(new ArrayList<>(list), new ArrayList<>(), result);
        return result;
    }

    private static void permute(List<Player> currentToGenerate, List<Player> alreadyGenerated, ArrayList<ArrayList<Player>> result) {
        if (currentToGenerate.isEmpty()) {
            result.add(new ArrayList<>(alreadyGenerated));
            return;
        }

        for (int i = 0; i < currentToGenerate.size(); i++) {
            ArrayList<Player> nextCurrent = new ArrayList<>(currentToGenerate);
            ArrayList<Player> nextAlreadyGenerated = new ArrayList<>(alreadyGenerated);
            nextAlreadyGenerated.add(nextCurrent.remove(i));
            permute(nextCurrent, nextAlreadyGenerated, result);
        }
    }

    private ArrayList<RoundPair> TryAllParticipantCombinations(ArrayList<Player> participants, ArrayList<RoundDraw> previousDraws)
    {
        ArrayList<RoundPair> prohibitedMatches = HelpMethods.GetAllMatches(previousDraws);
        for (ArrayList<Player> permutace : generatePermutations(participants))
        {
            var attempt = TryAllParticipantCombination(permutace, prohibitedMatches);

            if(attempt != null)
            {
                return attempt;
            }
        }
        return null;
    }

    private ArrayList<RoundPair> TryAllParticipantCombination(ArrayList<Player> participants, ArrayList<RoundPair> prohibitedMatches)
    {
        ArrayList<RoundPair> generatedPairs = new ArrayList<>();
        for (int i = 0; i < participants.size(); i += 2)
        {
            var newPair = new RoundPair(participants.get(i), participants.get(i + 1));
            if (!ViolatesAbsoluteCriteriaC1(prohibitedMatches, newPair))
            {
                generatedPairs.add(newPair);
            }
            else
            {
                return null;
            }
        }
        return generatedPairs;
    }


    ///modifed code from https://www.codeproject.com/Articles/43767/A-C-List-Permutation-Iterator (this method)
    private void RotateRight(ArrayList<PlayerWithPoints> sequence, int count)
    {
        PlayerWithPoints tmp = sequence.get(count - 1);
        sequence.remove(count - 1);
        sequence.add(0, tmp);
    }

    private ArrayList<RoundPair> GetBestCandidate(ArrayList<RoundPair> allRoundPairsInTournamentList, ArrayList<PlayerWithPoints> S1, ArrayList<PlayerWithPoints> S2, int remainToPermutate, int fromBeginning, ArrayList<RoundPair> correctGeneratedPairs)
    {
        ArrayList<ArrayList<RoundPair>> candidates = new ArrayList<>();
        if (_currentBestResultLength < remainToPermutate + correctGeneratedPairs.size())
        {
            for (int i = 0; i < remainToPermutate; i++)
            {
                int index1 = S1.size() - (fromBeginning + 1);
                int index2 = S2.size() - (fromBeginning + 1);
                RoundPair newPair = new RoundPair(S1.get(index1).Player, S2.get(index2).Player);

                if (!ViolatesAbsoluteCriteria(allRoundPairsInTournamentList, newPair))
                {
                    var copy = new ArrayList<RoundPair>(correctGeneratedPairs);
                    copy.add(newPair);

                    if(copy.size() > _currentBestResultLength)
                    {
                        _currentBestResultLength = copy.size();
                    }
                    candidates.add(GetBestCandidate(allRoundPairsInTournamentList, S1, S2, remainToPermutate - 1, fromBeginning + 1, copy));
                }
                else
                {
                    candidates.add(GetBestCandidate(allRoundPairsInTournamentList, S1, S2, remainToPermutate - 1, fromBeginning + 1, correctGeneratedPairs));
                }

                RotateRight(S1, remainToPermutate);
            }
        }

        candidates.add(correctGeneratedPairs);

        return candidates.stream().sorted(Comparator.comparingInt(ArrayList::size)).collect(Collectors.toCollection(ArrayList::new)).reversed().getFirst();
    }

    private ArrayList<RoundPair> GetBestGroupPairing(ArrayList<RoundPair> allRoundPairsInTournamentList, ArrayList<PlayerWithPoints> S1, ArrayList<PlayerWithPoints> S2)
    {
        _currentBestResultLength = 0;

        return GetBestCandidate(allRoundPairsInTournamentList, S1, S2, S1.size(), 0, new ArrayList<>());
    }

    private int _currentBestResultLength = 0;

    private ProcessedGroup MakePairs(ArrayList<RoundPair> allRoundPairsInTournament, ArrayList<PlayerWithPoints> S1, ArrayList<PlayerWithPoints> S2)
    {
        ArrayList<RoundPair> pairedPlayers = GetBestGroupPairing(allRoundPairsInTournament, S1, S2);


        ArrayList<PlayerWithPoints> unpaired = new ArrayList<>();

        for (var participant : S1)
        {
            if (!HelpMethods.IsPaired(pairedPlayers, participant.Player))
            {
                unpaired.add(participant);
            }
        }

        for (var participant : S2)
        {
            if (!HelpMethods.IsPaired(pairedPlayers, participant.Player))
            {
                unpaired.add(participant);
            }
        }

        return new ProcessedGroup(pairedPlayers, unpaired);

    }

    private ProcessedGroup ProcessGroup(ArrayList<RoundPair> allPreviousMatches, ArrayList<PlayerWithPoints> playersWithPoints, ArrayList<PlayerWithPoints> fromPreviousGroup)
    {
        fromPreviousGroup.addAll(playersWithPoints);
        playersWithPoints = fromPreviousGroup;

        Tuple<ArrayList<PlayerWithPoints>> result = SplitIntoHalves(playersWithPoints);
        ArrayList<PlayerWithPoints> group1 = result.Item1;
        ArrayList<PlayerWithPoints> group2 = result.Item2;
        ArrayList<PlayerWithPoints> leftover = result.Item3;

        ProcessedGroup madePairs = MakePairs(allPreviousMatches, group1, group2);
        ArrayList<RoundPair> pairedPlayers = madePairs.Item1;
        leftover.addAll(madePairs.Item2);

        return new ProcessedGroup(pairedPlayers, leftover);
    }

    private Tuple<ArrayList<PlayerWithPoints>> SplitIntoHalves(List<PlayerWithPoints> playersWithPoints)
    {
        int numberOfPlayers = playersWithPoints.size();

        if (numberOfPlayers == 0)
        {
            return new Tuple<>(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        if (numberOfPlayers == 1)
        {
            var tmp = new ArrayList<PlayerWithPoints>();
            tmp.add(playersWithPoints.get(0));
            return new Tuple<>(new ArrayList<>(), new ArrayList<>(), tmp);
        }

        playersWithPoints = playersWithPoints.stream().sorted(Comparator.comparingDouble(PlayerWithPoints::GetScore).reversed()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);


        if (numberOfPlayers % 2 == 0)
        {
            ArrayList<PlayerWithPoints> firstHalf = new ArrayList<>(playersWithPoints.subList(0, (numberOfPlayers) / 2));
            ArrayList<PlayerWithPoints> secondHalf = new ArrayList<>(playersWithPoints.subList(numberOfPlayers / 2, numberOfPlayers));

            return new Tuple<>(firstHalf, secondHalf, new ArrayList<>());
        }
        else
        {
            ArrayList<PlayerWithPoints> firstHalf = new ArrayList<>(playersWithPoints.subList(0, numberOfPlayers / 2));
            ArrayList<PlayerWithPoints> secondHalf = new ArrayList<>(playersWithPoints.subList(numberOfPlayers / 2, numberOfPlayers - 1));

            var tmp = new ArrayList<PlayerWithPoints>();
            tmp.add(playersWithPoints.get(numberOfPlayers - 1));

            return new Tuple<>(firstHalf, secondHalf, tmp);
        }
    }

    private ArrayList<ArrayList<PlayerWithPoints>> SortIntoGroups(ArrayList<Player> participants, ArrayList<RoundDraw> previousDraws)
    {
        ArrayList<PlayerWithPoints> playersWithPoints = GetCurrentStandings(previousDraws, false);

        ArrayList<ArrayList<PlayerWithPoints>> groups = new ArrayList<>();


        double maximumPoints = playersWithPoints.stream().mapToDouble(PlayerWithPoints::GetScore).max().orElseThrow();

        //number of groups based on number of different results in players with points
        for (int i = 0; i <= maximumPoints * 2; i += 1)
        {
            groups.add(new ArrayList<>());
        }

        for (var playerWithPoints : playersWithPoints)
        {
            groups.get(groups.size() - 1 - (int)(playerWithPoints.Score * 2)).add(playerWithPoints);
        }

        return groups;

    }

    private boolean ViolatesAbsoluteCriteria(ArrayList<RoundPair> roundPairs, RoundPair pairToCheck)
    {
        if(ViolatesAbsoluteCriteriaC1(roundPairs, pairToCheck))
        {
            return true;
        }
        return false;
    }

    private boolean ViolatesAbsoluteCriteriaC1(ArrayList<RoundPair> roundPairs, RoundPair pairToCheck)
    {
        return roundPairs.stream().anyMatch(x -> x.PlaysInPair(pairToCheck.White) && x.PlaysInPair(pairToCheck.Black));
    }


}
