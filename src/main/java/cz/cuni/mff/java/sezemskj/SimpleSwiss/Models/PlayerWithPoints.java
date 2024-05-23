package cz.cuni.mff.java.sezemskj.SimpleSwiss.Models;

public class PlayerWithPoints {

    public Player Player;

    public double Score;

    public double GetScore(){
        return Score;
    }

    public double Buchholz;

    public double getBuchholz(){
        return Buchholz;
    }

    public double BuchholzShortened;

    public double getBuchholzShortened(){
        return BuchholzShortened;
    }

    public double SonnenbornBerger;

    public double getSonnenbornBerger(){
        return SonnenbornBerger;
    }

    public double BlackPiecesPlayed;

    public double getBlackPiecesPlayed(){
        return BlackPiecesPlayed;
    }

    public PlayerWithPoints(Player player)
    {
        Player = player;
        Score = 0;
    }

    public void AddScore(double points)
    {
        Score += points;
    }


}
