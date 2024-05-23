package cz.cuni.mff.java.sezemskj.SimpleSwiss.Models;

import java.util.Objects;

public class RoundPair {


    public Player White;
    public Player Black;

    public double WhitePoints = 0;

    public double BlackPoints = 0;

    public RoundPair(){

    }

    public boolean isDraw(){
        return WhitePoints == BlackPoints;
    }


    public RoundPair(Player player1, Player player2){

        White = player1;
        Black = player2;
    }

    public Player GetWinner()
    {
        if (WhitePoints > BlackPoints)
        {
            return White;
        }

        return Black;
    }

    public void SwapParticipants()
    {
        Player tmp = White;
        White = Black;
        Black = tmp;
    }

    public double GetScoreOfParticipant(Player participant)
    {
        if(participant == null)
        {
            return 0;
        }
        if (participant.Id.equals(White.Id))
        {
            return WhitePoints;
        }
        if(participant.Id.equals(Black.Id))
        {
            return BlackPoints;
        }
        return 0;
    }

    public boolean PlaysInPair(Player player)
    {
        if(player == null && (White == null || Black == null))
        {
            return true;
        }
        if(player == null)
        {
            return false;
        }
        if(Objects.equals(White.Id, player.Id))
        {
            return true;
        }
        if(Objects.equals(Black.Id, player.Id))
        {
            return true;
        }
        return false;
    }

    public Player GetOpponent(Player participant)
    {
        if(Objects.equals(White.Id, participant.Id))
        {
            return Black;
        }
        if(Objects.equals(Black.Id, participant.Id))
        {
            return White;
        }
        return White;
    }

}
