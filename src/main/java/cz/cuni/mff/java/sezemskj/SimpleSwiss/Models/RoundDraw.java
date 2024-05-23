package cz.cuni.mff.java.sezemskj.SimpleSwiss.Models;

import java.util.ArrayList;

public class RoundDraw {

    public ArrayList<RoundPair> _roundPairs = new ArrayList<>();

    public RoundDraw()
    {
        _roundPairs = new ArrayList<>();
    }

    public RoundDraw(ArrayList<RoundPair> pairs)
    {
        _roundPairs = pairs;
    }

    public void AddPair(RoundPair pair)
    {
        _roundPairs.add(pair);
    }

    public void AddRange(ArrayList<RoundPair> pairs)
    {
        for (RoundPair pair : pairs)
        {
            AddPair(pair);
        }
    }

    public ArrayList<RoundPair> GetRoundPairs()
    {
        return _roundPairs;
    }


    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder();

        for (RoundPair pair : _roundPairs)
        {
            toReturn.append(pair.toString());
            toReturn.append(" | ");
        }

        return toReturn.toString();
    }


}
